package interface_adapters.favoritesIA;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the current state of a user's favorite stocks.
 * This class manages a collection of favorited stock symbols,
 * providing methods to add, remove, and query favorite stocks.
 * It ensures encapsulation by returning defensive copies of the
 * favorite stocks set.
 */
public class FavoriteStockState {
    /**
     * Internal set to store the symbols of favorited stocks.
     * Uses a HashSet for efficient lookup and unique storage.
     */
    private Set<String> favoriteStocks;

    /**
     * Constructs a new FavoriteStockState with an empty set of favorite stocks.
     *
     */
    public FavoriteStockState() {
        // Initialize with an empty HashSet to store favorite stock symbols
        this.favoriteStocks = new HashSet<>();
    }

    /**
     * Adds a stock symbol to the set of favorite stocks.
     * If the symbol is already present, this method has no effect
     * due to the Set's unique element constraint.
     * @param symbol The stock symbol to add to favorites
     */
    public void addFavorite(String symbol) {
        favoriteStocks.add(symbol);
    }

    /**
     * Removes a stock symbol from the set of favorite stocks.
     * If the symbol is not present in the favorites, this method
     * has no effect.
     *
     * @param symbol The stock symbol to remove from favorites
     */
    public void removeFavorite(String symbol) {
        favoriteStocks.remove(symbol);
    }

    /**
     * Retrieves a defensive copy of the current favorite stocks.
     * Returns a new HashSet containing all favorited stock symbols,
     * preventing direct modification of the internal collection.
     *
     * @return A new Set containing the favorited stock symbols
     */
    public Set<String> getFavoriteStocks() {
        return new HashSet<>(favoriteStocks);
    }

    /**
     * Checks if a specific stock symbol is currently favorited.
     *
     * @param symbol The stock symbol to check for favorite status
     * @return true if the symbol is in the favorites set, false otherwise
     */
    public boolean isFavorited(String symbol) {
        return favoriteStocks.contains(symbol);
    }
}
