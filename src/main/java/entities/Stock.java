package main.java.entities;

/**
 * Stock: the stocks of a single company.
 */
public class Stock {
    private String company;
    private String symbol;

    private Stock(String company, String symbol) {
        this.company = company;
        this.symbol = symbol;
    }

    public String getCompany() {
        return company;
    }

    public String getSymbol() {
        return symbol;
    }
}
