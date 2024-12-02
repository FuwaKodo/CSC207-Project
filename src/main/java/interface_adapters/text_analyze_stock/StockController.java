package interface_adapters.text_analyze_stock;

import use_cases.text_analyze_stock.FutureStockInputBoundary;

/**
 * The class for the stock controller.
 */
public class StockController {
    private final FutureStockInputBoundary futureStockInteractor;

    /**
     * The future stock Interactor.
     * @param futureStockInteractor The future stock Interactor.
     */
    public StockController(FutureStockInputBoundary futureStockInteractor) {
        this.futureStockInteractor = futureStockInteractor;
    }

    /**
     * The future stock Interactor.
     * @param stockName The stock name.
     * @param currentPrice The current price.
     * @param startingPrice The initial price.
     */
    public void execute(String stockName,
                        double currentPrice, double startingPrice) {
        futureStockInteractor.execute(stockName, currentPrice, startingPrice);
    }
}
