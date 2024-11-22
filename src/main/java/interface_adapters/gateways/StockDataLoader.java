package interface_adapters.gateways;

import entities.MetricValues;
import entities.SharePrices;

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

    /**
     * Get historical share prices of a stock.
     * @return list of share prices
     */
    SharePrices getSharePrices();

    /**
     * Get historical earnings of a stock.
     * @return list of earnings
     */
    MetricValues getEarnings();

    /**
     * Get volumes of a stock.
     * @return volume of stock
     */
    MetricValues getVolumes();
}
