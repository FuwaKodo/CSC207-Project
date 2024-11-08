package main.java.use_cases.load_data;

import java.util.List;

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
    List<Double> getSharePrices();

    /**
     * Get historical earnings of a stock.
     * @return list of earnings
     */
    List<Double> getEarnings();
}
