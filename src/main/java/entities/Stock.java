package entities;

import interface_adapters.gateways.StockDataLoader;

/**
 * Stock: the stocks of a single company.
 */
public class Stock {
    private final String company;
    private final String symbol;
    private final Metrics metrics;

    public Stock(StockDataLoader loader, String company, String symbol) {
        // TODO: Issue here because getCompany no longer possible!
        // Getting symbol and company must be obtained from different instantiation
        this.symbol = symbol;
        this.company = company;
        metrics = null;
    }

    public String getCompany() {
        return company;
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * Get share price at a specific day.
     * @param day the number of days before the latest data point
     * @return share price
     */
    public Double getSharePrice(int day) {
        return metrics.sharePrice(day);
    }

    /**
     * Get volume at a specific day.
     * @param day the number of days before the latest data point
     * @return volume
     */
    public Double getVolume(int day) {
        return metrics.volume(day);
    }
}
