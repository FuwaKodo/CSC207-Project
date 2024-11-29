package use_cases.favorites;

/**
 * Data class that encapsulates the input parameters required for favorite stock operations.
 * This class follows the immutable pattern to ensure data integrity during use case execution.
 */
public class FavoriteStockInputData {

    /** The stock symbol identifier (e.g., "AAPL" for Apple Inc.) */
    private final String stockSymbol;

    /**
     * Constructs a new FavoriteStockInputData instance.
     *
     * @param stockSymbol The unique identifier/symbol of the stock
     *                    to be used in favorite operations
     * @throws IllegalArgumentException if stockSymbol is null or empty
     */
    public FavoriteStockInputData(String stockSymbol) {
        if (stockSymbol == null || stockSymbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Stock symbol cannot be null or empty");
        }
        this.stockSymbol = stockSymbol;
    }

    /**
     * Retrieves the stock symbol.
     *
     * @return The stock symbol associated with this input data
     */
    public String getStockSymbol() {
        return stockSymbol;
    }
}