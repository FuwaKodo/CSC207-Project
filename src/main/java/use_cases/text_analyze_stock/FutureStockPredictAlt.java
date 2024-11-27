package use_cases.text_analyze_stock;

public class FutureStockPredictAlt {

    // Method to calculate the stock price in 1 year
    public static double calculateStockPrice(double current, double initial) {
        // Formula: P1 = P0 * (1 + (E1 - E0) / E0)
        double priceInOneYear = current * (1 + (current - initial) / 100);
        return priceInOneYear;
    }
}