package Test;

import main.ValuationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ValuationServiceTest {
    @Test
    public void testCalculateValuation() {
        ValuationService service = new ValuationService();
        service.loadProductData("data.csv");
        service.loadCurrencyData("currencies.csv");
        service.loadMatchingData("matchings.csv");
        service.calculateValuation("top_products.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader("top_products.csv"))) {
            // Skip header
            reader.readLine();

            // Check first line
            String line = reader.readLine();
            Assertions.assertEquals("1,2000.00,1000.00,PLN,1", line);

            // Check second line
            line = reader.readLine();
            Assertions.assertEquals("2,20000.00,10000.00,PLN,1", line);

            // Check third line
            line = reader.readLine();
            Assertions.assertEquals("3,9600.00,3200.00,EUR,1", line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
