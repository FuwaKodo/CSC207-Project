package use_cases.favorites;

import java.util.Set;

/**
 * Output boundary interface for favorite stock operations.
 * This interface defines the contract for presenting the results
 * of favorite stock operations to the user interface layer.
 */
public interface FavoriteStockOutputBoundary {

    /**
     * Presents the result of a favorite toggle operation.
     * This method should be called after a stock's favorite status has been changed.
     *
     * @param symbol      The stock symbol that was toggled
     * @param isFavorited The new favorite status of the stock:
     *                    true if the stock was added to favorites,
     *                    false if it was removed
     */
    void presentFavoriteToggled(String symbol, boolean isFavorited);

    /**
     * Presents the complete list of favorite stocks.
     * This method should be called in response to a request for all favorite stocks.
     *
     * @param favorites A set of stock symbols representing all currently
     *                 favorited stocks. An empty set indicates no favorites.
     */
    void presentFavorites(Set<String> favorites);
}