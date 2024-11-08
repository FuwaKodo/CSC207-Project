package entities;

import use_cases.load_data.StockDataLoader;

import java.util.List;

/**
 * Stock: the stocks of a single company.
 */
public class Stock {
    private final String company;
    private final String symbol;
    private final Metrics metrics;

    public Stock(StockDataLoader loader) {
        this.company = loader.getCompany();
        this.symbol = loader.getSymbol();

        List<Double> sharePrices = loader.getSharePrices();
        List<Double> earnings = loader.getEarnings();
        this.metrics = new Metrics(sharePrices, earnings);
    }

    public String getCompany() {
        return company;
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * Get share price at a specific day.
     * @param day the number of days before today
     * @return share price
     */
    public Double getSharePrice(int day) {
        return metrics.sharePrice(day);
    }
}
