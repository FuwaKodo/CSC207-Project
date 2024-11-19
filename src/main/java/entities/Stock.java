package main.java.entities;

import main.java.interface_adapters.gateways.StockDataLoader;

import java.time.LocalDate;

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

        this.metrics = new Metrics(
                loader.getSharePrices(),
                loader.getEarnings(),
                loader.getVolumes()
        );
    }

    public String getCompany() {
        return company;
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * Get share price at a specific date.
     * @param date the date. If there is no value for the date, return the
     *             value on the date immediately after it.
     * @return share price
     */
    public Double getSharePrice(LocalDate date) {
        return metrics.sharePrice(date);
    }

    /**
     * Get volume on a date.
     * @param date the date. If there is no value for the date, return
     *             the value on the date immediately after it.
     * @return volume
     */
    public Double getVolume(LocalDate date) {
        return metrics.volume(date);
    }
}
