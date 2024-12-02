package use_cases.text_analyze_stock;

public interface FutureStockInputBoundary {
    void execute(String stockName,
                 double currentPrice, double startingPrice);
}
