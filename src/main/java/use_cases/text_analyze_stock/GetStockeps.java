package use_cases.text_analyze_stock;

/**
 * Provides functionality to retrieve the Earnings Per Share (EPS) value for a specific stock ticker.
 * This class maintains a predefined list of stock tickers and their corresponding EPS values.
 * It includes a method to look up the EPS value for a given stock ticker.
 */
public final class GetStockeps {

    // Create an array to store stock tickers and their EPS values
    private static final String[] STOCK_TICKERS = {"AAPL", "NVDA", "MFC", "L.TO", "INTC"};
    private static final double[] STOCK_EPS_VALUES = {6.59, 2.13, 2.12, 4.59, -0.40};

    // Private constructor to prevent instantiation
    private GetStockeps() {
        // Private constructor does not allow instantiation
    }
    // Getter method to return EPS for a given stock ticker
    /**
     * Retrieves the Earnings Per Share (EPS) value for a specific stock ticker.
     *
     * @param stock Input a stock ticker as a String.
     * @return The EPS value of the respective stock, or null if the ticker is not found.
     */

    public static Double geteps(String stock) {
        Double result = null;
        for (int i = 0; i < STOCK_TICKERS.length; i++) {
            if (STOCK_TICKERS[i].equals(stock)) {
                result = STOCK_EPS_VALUES[i];
                break;
            }
        }
        return result;
    }
}
