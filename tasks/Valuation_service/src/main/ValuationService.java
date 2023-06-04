package main;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ValuationService {
    private List<Product> products;
    private List<Currency> currencies;
    private List<Matching> matchings;

    public ValuationService() {
        products = new ArrayList<>();
        currencies = new ArrayList<>();
        matchings = new ArrayList<>();
    }

    public void loadProductData(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean skipFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0]);
                double price = Double.parseDouble(values[1]);
                String currency = values[2];
                int quantity = Integer.parseInt(values[3]);
                int matchingId = Integer.parseInt(values[4]);
                Product product = new Product(id, price, currency, quantity, matchingId);
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCurrencyData(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean skipFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                String code = values[0];
                double ratio = Double.parseDouble(values[1]);
                Currency currency = new Currency(code, ratio);
                currencies.add(currency);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMatchingData(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean skipFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                int matchingId = Integer.parseInt(values[0]);
                int topPricedCount = Integer.parseInt(values[1]);
                Matching matching = new Matching(matchingId, topPricedCount);
                matchings.add(matching);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculateValuation(String outputFilename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilename))) {
            writer.println("matching_id,total_price,avg_price,currency,ignored_products_count");

            for (Matching matching : matchings) {
                int matchingId = matching.getMatching_id();
                int topPricedCount = matching.getTop_priced_count();

                List<Product> matchingProducts = products.stream()
                        .filter(p -> p.getMatching_id() == matchingId)
                        .sorted(Comparator.comparingDouble(p -> -p.getPrice() * p.getQuantity()))
                        .limit(topPricedCount)
                        .collect(Collectors.toList());

                double totalPrice = matchingProducts.stream()
                        .mapToDouble(p -> p.getPrice() * p.getQuantity())
                        .sum();

                int ignoredProductsCount = products.stream()
                        .filter(p -> p.getMatching_id() == matchingId && !matchingProducts.contains(p))
                        .collect(Collectors.toList())
                        .size();

                double avgPrice = totalPrice / topPricedCount;

                String currency = matchingProducts.isEmpty() ? "PLN" : matchingProducts.get(0).getCurrency();

                writer.printf("%d,%.2f,%.2f,%s,%d\n", matchingId, totalPrice, avgPrice, currency, ignoredProductsCount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ValuationService service = new ValuationService();
        service.loadProductData("data.csv");
        service.loadCurrencyData("currencies.csv");
        service.loadMatchingData("matchings.csv");
        service.calculateValuation("top_products.csv");
    }
}
