package use_cases.compare_stocks;

import entities.Stock;

/**
 * Comparator that compares two stocks.
 */
public interface StockComparator {

    /**
     * Compares two stocks.
     * @param stock1 first stock
     * @param stock2 other stock
     * @return the result
     */
    int compare(Stock stock1, Stock stock2);
}
