package use_cases.text_analyze_stock;

public class test_analyze_stock {

    // Method to calculate the projected price based on stock name, current price, and input price
    public static double calculateProjectedPrice(String stockName, double currentPrice, double startingPrice) {
        // Get the EPS for the given stock name
        Double EPS = GetStockEPS.getEPS(stockName);

        // Initialize projected price variable
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

        // Print or log the action (buy, hold, sell)
        String action;
        if (projectedPrice1 - currentPrice >= 3) {
            action = "buy";
        } else if (projectedPrice1 - currentPrice >= -3 && projectedPrice1 - currentPrice <= 3) {
            action = "hold";
        } else {
            action = "sell";
        }

        System.out.println("The current price of stock " + stockName + " is " + currentPrice + ". "
                        + " The projected price of the stock in 1 year is " + projectedPrice1 + ". "
                        + " The projected price of the stock in 2 years is " + projectedPrice2 + ". "
                        + " The projected price of the stock in 2 years is " + projectedPrice3 + ". "
                        + " Based on current trends, this stock is a " + action);

        // Return the calculated projected price
        return projectedPrice1;
    }


    // Test method to demonstrate functionality
    public static void main(String[] args) {
        // Example usage:
        double projectedPrice = test_analyze_stock.calculateProjectedPrice("MFC", 44.81, 46.23);
        System.out.println("Projected Price: " + projectedPrice);
    }
}
