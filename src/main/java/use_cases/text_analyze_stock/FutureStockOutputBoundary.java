package use_cases.text_analyze_stock;

/**
 * Output boundary interface for stock analysis.
 */
public interface FutureStockOutputBoundary {
    /**
     * Displays the result of stock analysis.
     * @param result The stock analysis result to display
     */
    void displayResult(StockAnalysisResult result);
}
