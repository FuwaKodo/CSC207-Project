// use_cases/favorites/FavoriteStockOutputBoundary.java
package use_cases.favorites;

import java.util.Set;

public interface FavoriteStockOutputBoundary {
    void presentFavoriteToggled(String symbol, boolean isFavorited);
    void presentFavorites(Set<String> favorites);
}