package use_cases.text_analyze_stock;

import app.Constants;

/**
 * A utility class for analyzing stock-related data and predicting future stock behavior.
 * This class includes methods for financial calculations, such as the Graham number.
 */
public final class FutureStockPredict {
    // Private constructor to prevent instantiation
    private FutureStockPredict() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    /**
     * Method for calculating Graham's number.
     *
     * @param eps Earnings per share
     * @param currentprice The current price of the stock
     * @return The projected price of the stock in a year from now, or null if the current price is negative
     */

    public static Double calculateGrahamNumber(double eps, double currentprice) {
        Double detect = null;
        if (currentprice >= 0) {
            detect = Math.sqrt(Constants.GRAHAM_NUMBER * eps * currentprice);
        }
        return detect;
    }
}
