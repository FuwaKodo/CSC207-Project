package test;

import java.util.Date;

import entities.MetricValues;
import entities.SharePrices;
import use_cases.StockDataInterface;

public class MockStockDataLoader implements StockDataInterface {
    public final String company;
    public final String symbol;

    public MockStockDataLoader() {
        company = "Apple";
        symbol = "AAPL";
    }

    public MockStockDataLoader(String company, String symbol) {
        this.company = company;
        this.symbol = symbol;
    }

    @Override
    public SharePrices getSharePrices(String stockSymbol, Date startDate, Date endDate) {
        return MockMetrics.makeSharePrices();
    }

    /*@Override
    public MetricValues getEarnings() {
        return MockMetrics.makeMetricValues();
    }*/

    @Override
    public MetricValues getVolumes(String stockSymbol, Date startDate, Date endDate) {
        return MockMetrics.makeMetricValues();
    }

    @Override
    public Double getVolume(String stockSymbol, Date date) {
        return 0.0;
    }

    @Override
    public Double getAfterHour(String stockSymbol, Date date) {
        return 0.0;
    }

    @Override
    public MetricValues getAfterHours(String stockSymbol, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public Double getPremarket(String stockSymbol, Date date) {
        return 0.0;
    }

    @Override
    public MetricValues getPremarkets(String stockSymbol, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public SharePrices getSharePrice(String stockSymbol, Date date) {
        return null;
    }
}
