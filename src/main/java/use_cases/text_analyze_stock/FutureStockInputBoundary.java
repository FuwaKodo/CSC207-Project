package use_cases.text_analyze_stock;

/**
 * Interface for analyzing future stock data.
 */
public interface FutureStockInputBoundary {
    /**
     * Executes the analysis of stock data.
     *
     * @param stockName     the name of the stock to analyze
     * @param currentPrice  the current price of the stock
     * @param startingPrice the initial price of the stock
     */
    void execute(String stockName,
                 double currentPrice, double startingPrice);
}
