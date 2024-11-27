package use_cases.text_analyze_stock;

public class FutureStockPredict {
    // Method to calculate the Graham number
    public static double calculateGrahamNumber(double eps, double current_price) {
        return Math.sqrt(22.5 * eps * current_price);
    }
}
