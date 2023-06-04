package main;

class Currency {
    private String currency_code;
    private double ratio;

    public Currency(String currency_code, double ratio) {
        this.currency_code = currency_code;
        this.ratio = ratio;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public double getRatio() {
        return ratio;
    }
}
