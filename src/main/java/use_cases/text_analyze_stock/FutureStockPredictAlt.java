package use_cases.text_analyze_stock;

/**
 * The class predicts the future stock using a compounded formula.
 */
public class FutureStockPredictAlt {
    /**
     * This method calculateStockPrice.
     * @param current The current price of the stock
     * @param initial The price of the stock from 1 year ago
     * @return The price of the stock in 1 year from now.
     */
    public static Double calculateStockPrice(double current, double initial) {
        Double priceInOneYear = null;
        // Formula: P1 = P0 * (1 + (E1 - E0) / E0)
        if (current >= 0 && initial >= 0) {
            priceInOneYear = current * (1 + (current - initial) / 100);
        }
        return priceInOneYear;
    }
}
