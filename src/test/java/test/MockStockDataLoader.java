/*
package test;

import entities.MetricValues;
import entities.SharePrices;
import interface_adapters.gateways.StockDataLoader;

public class MockStockDataLoader implements StockDataLoader {
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
    public String getCompany() {
        return company;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public SharePrices getSharePrices() {
        return MockMetrics.makeSharePrices();
    }

    @Override
    public MetricValues getEarnings() {
        return MockMetrics.makeMetricValues();
    }

    @Override
    public MetricValues getVolumes() {
        return MockMetrics.makeMetricValues();
    }

    @Override
    public MetricValues getDividends() {
        return MockMetrics.makeMetricValues();
    }
}
*/
