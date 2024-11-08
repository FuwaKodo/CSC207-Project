package main.java.use_cases.load_data;

import main.java.entities.MetricValues;
import main.java.entities.SharePrices;

public interface StockDataLoader {
    String getCompany();
    String getSymbol();
    SharePrices getSharePrices();
    MetricValues getEarnings();
    MetricValues getVolumes();
}
