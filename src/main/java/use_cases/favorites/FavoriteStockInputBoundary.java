package use_cases.favorites;

/**
 * Input boundary interface for favorite stock operations.
 * This interface defines the contract for handling favorite stock-related use cases.
 */
public interface FavoriteStockInputBoundary {

    /**
     * Toggles the favorite status of a stock.
     * If the stock is not favorited, it will be added to favorites.
     * If the stock is already favorited, it will be removed from favorites.
     *
     * @param favoriteStockInputData The input data containing stock information
     *                               required for the toggle operation
     */
    void toggleFavorite(FavoriteStockInputData favoriteStockInputData);

    /**
     * Retrieves all favorite stocks for the current user.
     * This method should trigger the process of fetching and returning
     * all stocks marked as favorites.
     */
    void getFavorites();
}
