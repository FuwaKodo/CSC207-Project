package interface_adapters.text_analyze_stock;

import use_cases.text_analyze_stock.FutureStockInputBoundary;

public class StockController {
    private final FutureStockInputBoundary futureStockInteractor;
    public StockController(FutureStockInputBoundary futureStockInteractor) {
        this.futureStockInteractor = futureStockInteractor;
    }
    public void execute(String stockName,
                        double currentPrice, double startingPrice){
        futureStockInteractor.execute(stockName, currentPrice, startingPrice);
    }
}
