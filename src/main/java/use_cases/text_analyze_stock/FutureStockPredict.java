package use_cases.text_analyze_stock;

/**
 * A utility class for analyzing stock-related data and predicting future stock behavior.
 * This class includes methods for financial calculations, such as the Graham number.
 */
public class FutureStockPredict {
    /**
     * Method for calculating Graham's number.
     *
     * @param eps Earnings per share
     * @param current_price The current price of the stock
     * @return The projected price of the stock in a year from now, or null if the current price is negative
     */
    public static Double calculateGrahamNumber(double eps, double current_price) {
        Double detect = null;
        if (current_price >= 0) {
            detect = Math.sqrt(22.5 * eps * current_price);
        }
        return detect;
    }
}
