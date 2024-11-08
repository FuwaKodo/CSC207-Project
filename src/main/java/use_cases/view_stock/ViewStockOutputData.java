package main.java.use_cases.view_stock;

/**
 * The output data for this use case.
 */
public class ViewStockOutputData {
    private final String company;
    private final String symbol;

    public ViewStockOutputData(String company, String symbol) {
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
