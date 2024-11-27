package use_cases.text_analyze_stock;

public class TestAnalyzeStock {

    public static void main(String[] args) {
        // Perform stock analysis
        StockAnalysisResult result = AnalyzeStock.calculateProjectedPrice("L.TO", 180.36, 126);

        // Use the presenter to display the result
        StockPresenter.displayStockAnalysis(result);
    }
}