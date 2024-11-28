package use_cases.text_analyze_stock;

// Use Case for Analyzing Stock
public class AnalyzeStock {

    // Method to calculate the projected prices and determine action
    public static StockAnalysisResult calculateProjectedPrice(String stockName, double currentPrice, double startingPrice) {
        Double EPS = StockEPSConstant.getEPS(stockName);

        double projectedPrice1;
        double projectedPrice2;
        double projectedPrice3;

        if (EPS != null && EPS > 0) {
            // If EPS > 0, calculate projected price using Graham number formula
            projectedPrice1 = FutureStockPredict.calculateGrahamNumber(EPS, currentPrice);
            projectedPrice2 = FutureStockPredict.calculateGrahamNumber(EPS, projectedPrice1);
            projectedPrice3 = FutureStockPredict.calculateGrahamNumber(EPS, projectedPrice2);
        } else {
            // If EPS is null or <= 0, use the alternative stock price prediction method
            projectedPrice1 = FutureStockPredictAlt.calculateStockPrice(currentPrice, startingPrice);
            projectedPrice2 = FutureStockPredictAlt.calculateStockPrice(projectedPrice1, currentPrice);
            projectedPrice3 = FutureStockPredictAlt.calculateStockPrice(projectedPrice2, currentPrice);
        }

        String action;
        if (projectedPrice1 - currentPrice >= 3) {
            action = "buy";
        } else if (projectedPrice1 - currentPrice >= -3 && projectedPrice1 - currentPrice <= 3) {
            action = "hold";
        } else {
            action = "sell";
        }

        // Return the result encapsulated in a StockAnalysisResult object
        return new StockAnalysisResult(stockName, currentPrice, projectedPrice1, projectedPrice2, projectedPrice3, action);
    }
}
