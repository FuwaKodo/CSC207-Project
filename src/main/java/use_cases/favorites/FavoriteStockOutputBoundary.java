package use_cases.favorites;

import java.util.Set;

/**
 * Output boundary interface for favorite stock operations.
 */
public interface FavoriteStockOutputBoundary {
    /**
     * Called when a stock's favorite status is toggled.
     *
     * @param symbol The stock symbol that was toggled
     * @param isFavorited The new favorite status of the stock
     */
    void presentFavoriteToggled(String symbol, boolean isFavorited);

    /**
     * Called to present a complete set of favorite stocks.
     *
     * @param favorites The set of all favorited stock symbols
     */
    void presentFavorites(Set<String> favorites);
}