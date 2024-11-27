package use_cases.favorites;

import java.util.HashSet;
import java.util.Set;

public class FavoriteStockInteractor implements FavoriteStockInputBoundary {
    private final FavoriteStockOutputBoundary favoriteStockPresenter;
    private final Set<String> favoritedStocks = new HashSet<>();

    public FavoriteStockInteractor(FavoriteStockOutputBoundary favoriteStockPresenter) {
        this.favoriteStockPresenter = favoriteStockPresenter;
    }

    @Override
    public void toggleFavorite(FavoriteStockInputData inputData) {
        String symbol = inputData.getStockSymbol();
        boolean isFavorited = !favoritedStocks.contains(symbol);

        if (isFavorited) {
            favoritedStocks.add(symbol);
        } else {
            favoritedStocks.remove(symbol);
        }

        favoriteStockPresenter.presentFavoriteToggled(symbol, isFavorited);
    }

    @Override
    public void getFavorites() {
        favoriteStockPresenter.presentFavorites(new HashSet<>(favoritedStocks));
    }
}