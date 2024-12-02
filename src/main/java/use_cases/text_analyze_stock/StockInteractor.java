package use_cases.text_analyze_stock;

import app.Constants;
import use_cases.StockDataInterface;

/**
 * A class containing the methods to analyze the stock properties and to issue predictions.
 */
// Use Case for Analyzing Stock
public class StockInteractor implements FutureStockInputBoundary {
    private final FutureStockOutputBoundary presenter;
    private final StockDataInterface dataLoader;
    public StockInteractor(FutureStockOutputBoundary presenter, StockDataInterface dataLoader) {
        this.presenter = presenter;
        this.dataLoader = dataLoader;
    }
    /**
     * This Class Analyzes Stock.
     * @param stockName The Name of the stock
     * @param currentPrice The current price of the stock
     * @param startingPrice The price of the stock 1 year ago
     * @return Projected Prices for the stock, and buying decision
     */

    @Override
    public void execute(String stockName,
                        double currentPrice, double startingPrice) {
        final Double eps = GetStockeps.geteps(stockName);

        final double projectedPrice1;
        final double projectedPrice2;
        final double projectedPrice3;

        if (eps != null && eps > 0) {
            // If EPS > 0, calculate projected price using Graham number formula
            projectedPrice1 = FutureStockPredict.calculateGrahamNumber(eps, currentPrice);
            projectedPrice2 = FutureStockPredict.calculateGrahamNumber(eps, projectedPrice1);
            projectedPrice3 = FutureStockPredict.calculateGrahamNumber(eps, projectedPrice2);
        }
        else {
            // If EPS is null or <= 0, use the alternative stock price prediction method
            projectedPrice1 = FutureStockPredictAlt.calculateStockPrice(currentPrice, startingPrice);
            projectedPrice2 = FutureStockPredictAlt.calculateStockPrice(projectedPrice1, currentPrice);
            projectedPrice3 = FutureStockPredictAlt.calculateStockPrice(projectedPrice2, currentPrice);
        }

        final String action;
        if (projectedPrice1 - currentPrice >= Constants.WANTED_DIFFERENCE) {
            action = "buy";
        }
        else if (projectedPrice1 - currentPrice >= -Constants.WANTED_DIFFERENCE_NEGATIVE && projectedPrice1
                - currentPrice <= Constants.WANTED_DIFFERENCE) {
            action = "hold";
        }
        else {
            action = "sell";
        }

        presenter.displayResult(new StockAnalysisResult(stockName, currentPrice, projectedPrice1,
                projectedPrice2, projectedPrice3, action));

    }
}

