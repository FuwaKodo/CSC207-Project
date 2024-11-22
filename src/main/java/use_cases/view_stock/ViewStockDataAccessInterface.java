package use_cases.view_stock;

import entities.Stock;

/**
 * Data access interface for the use case.
 */
public interface ViewStockDataAccessInterface {

    /**
     * Retrieves a stock based on its symbol.
     *
     * @param symbol the symbol of a stock
     * @return the stock corresponding to the symbol
     */
    Stock getStock(String symbol);
}
