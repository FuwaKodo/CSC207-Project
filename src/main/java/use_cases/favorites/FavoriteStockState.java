package use_cases.favorites;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the state of favorite stocks.
 */
public class FavoriteStockState {
    private Set<String> favoriteStocks;

    public FavoriteStockState() {
        this.favoriteStocks = new HashSet<>();
    }

    public void addFavorite(String symbol) {
        favoriteStocks.add(symbol);
    }

    public void removeFavorite(String symbol) {
        favoriteStocks.remove(symbol);
    }

    public Set<String> getFavoriteStocks() {
        return new HashSet<>(favoriteStocks);
    }

    public boolean isFavorited(String symbol) {
        return favoriteStocks.contains(symbol);
    }
}