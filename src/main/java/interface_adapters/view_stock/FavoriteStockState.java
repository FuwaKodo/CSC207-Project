package interface_adapters.view_stock;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the state of the user's favorite stocks, including
 * the collection of favorited stocks and any potential error messages.
 */
public class FavoriteStockState {

    /** A set to store the symbols of favorited stocks. */
    private Set<String> favoritedStocks = new HashSet<>();

    /** An optional error message related to favorite stock operations. */
    private String error = null;

    /**
     * Returns a copy of the current set of favorited stocks.
     *
     * @return a set containing the symbols of favorited stocks
     */
    public Set<String> getFavoritedStocks() {
        return new HashSet<>(favoritedStocks);
    }

    /**
     * Adds a stock symbol to the set of favorited stocks.
     *
     * @param symbol the stock symbol to be added to favorites
     */
    public void addFavorite(String symbol) {
        favoritedStocks.add(symbol);
    }

    /**
     * Removes a stock symbol from the set of favorited stocks.
     *
     * @param symbol the stock symbol to be removed from favorites
     */
    public void removeFavorite(String symbol) {
        favoritedStocks.remove(symbol);
    }
}