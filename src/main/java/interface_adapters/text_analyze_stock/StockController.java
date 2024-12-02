package interface_adapters.text_analyze_stock;

import use_cases.text_analyze_stock.FutureStockInputBoundary;

/**
 * The public class for the StockController.
 */
public class StockController {
    private final FutureStockInputBoundary futureStockInteractor;

    public StockController(FutureStockInputBoundary futureStockInteractor) {
        this.futureStockInteractor = futureStockInteractor;
    }

    /**
     * Executes based on stock parameters.
     * @param stockName The name of the stock.
     * @param currentPrice The current price of the stock.
     * @param startingPrice The initial price of the stock.
     */
    public void execute(String stockName,
                        double currentPrice, double startingPrice) {
        futureStockInteractor.execute(stockName, currentPrice, startingPrice);
    }
}
