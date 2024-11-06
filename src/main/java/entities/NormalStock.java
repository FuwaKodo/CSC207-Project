package main.java.entities;

import main.java.use_cases.load_data.StockDataLoader;

import java.util.List;

/**
 * An implementation of Stock.
 */
public class NormalStock implements Stock{
    private final String company;
    private final String symbol;
    private final Metrics metrics;

    public NormalStock(StockDataLoader loader) {
        this.company = loader.getCompany();
        this.symbol = loader.getSymbol();

        List<Double> sharePrices = loader.getSharePrices();
        List<Double> earnings = loader.getEarnings();
        this.metrics = new NormalMetrics(sharePrices, earnings);
    }

    @Override
    public String getCompany() {
        return company;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public Double getSharePrice(int day) {
        return metrics.sharePrice(day);
    }
}
