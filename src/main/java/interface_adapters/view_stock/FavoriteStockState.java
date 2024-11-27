package interface_adapters.view_stock;

import java.util.HashSet;
import java.util.Set;

public class FavoriteStockState {
    private Set<String> favoritedStocks = new HashSet<>();
    private String error = null;

    public Set<String> getFavoritedStocks() {
        return new HashSet<>(favoritedStocks);
    }

    public void addFavorite(String symbol) {
        favoritedStocks.add(symbol);
    }

    public void removeFavorite(String symbol) {
        favoritedStocks.remove(symbol);
    }
}