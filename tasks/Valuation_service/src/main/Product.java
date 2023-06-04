package main;

class Product {
    private int id;
    private double price;
    private String currency;
    private int quantity;
    private int matching_id;

    public Product(int id, double price, String currency, int quantity, int matching_id) {
        this.id = id;
        this.price = price;
        this.currency = currency;
        this.quantity = quantity;
        this.matching_id = matching_id;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMatching_id() {
        return matching_id;
    }
}
