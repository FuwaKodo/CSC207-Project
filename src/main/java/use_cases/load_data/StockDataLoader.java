package main.java.use_cases.load_data;

import main.java.entities.MetricValues;
import main.java.entities.SharePrices;

/**
 * Stores relevant data for creating a stock entity.
 */
public interface StockDataLoader {

    /**
     * Get company name.
     * @return the name of the company
     */
    String getCompany();

    /**
     * Get stock symbol.
     * @return the symbol of the stock
     */
    String getSymbol();
    SharePrices getSharePrices();
    MetricValues getEarnings();
    MetricValues getVolumes();

    /**
     * Get historical share prices of a stock.
     * @return list of share prices
     */
    List<Double> getSharePrices();

    /**
     * Get historical earnings of a stock.
     * @return list of earnings
     */
    List<Double> getEarnings();
}
