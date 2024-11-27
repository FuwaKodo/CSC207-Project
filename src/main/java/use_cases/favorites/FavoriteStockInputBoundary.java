package use_cases.favorites;

public interface FavoriteStockInputBoundary {
    void toggleFavorite(FavoriteStockInputData favoriteStockInputData);
    void getFavorites();
}