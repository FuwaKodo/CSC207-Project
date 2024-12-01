package interface_adapters.favoriteia;

import use_cases.favorites.FavoriteStockFileStorage;
import use_cases.favorites.FavoriteStockInputBoundary;
import use_cases.favorites.FavoriteStockInputData;
import use_cases.favorites.FavoriteStockOutputBoundary;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements the business logic for favorite stock operations.
 * This interactor manages the favorite status of stocks and communicates
 * with the presentation layer through the output boundary.
 */
public class FavoriteStockInteractor implements FavoriteStockInputBoundary {

    /** Presenter for handling output operations */
    private final FavoriteStockOutputBoundary favoriteStockPresenter;

    /** File storage for persisting favorite stocks */
    private final FavoriteStockFileStorage fileStorage;

    /** In-memory storage of favorited stock symbols */
    private final Set<String> favoritedStocks;

    /**
     * Constructs a new FavoriteStockInteractor.
     *
     * @param favoriteStockPresenter The presenter responsible for handling output operations
     * @throws IllegalArgumentException if favoriteStockPresenter is null
     */
    public FavoriteStockInteractor(FavoriteStockOutputBoundary favoriteStockPresenter) {
        if (favoriteStockPresenter == null) {
            throw new IllegalArgumentException("Favorite stock presenter cannot be null");
        }
        this.favoriteStockPresenter = favoriteStockPresenter;
        this.fileStorage = new FavoriteStockFileStorage();
        this.favoritedStocks = new HashSet<>(fileStorage.loadFavoriteStocks());
    }

    /**
     * Toggles the favorite status of a stock and notifies the presenter of the change.
     * If the stock is not in favorites, it will be added.
     * If the stock is already in favorites, it will be removed.
     * Changes are persisted to file storage.
     *
     * @param inputData The input data containing the stock symbol to toggle
     */
    @Override
    public void toggleFavorite(FavoriteStockInputData inputData) {
        String symbol = inputData.getStockSymbol();
        boolean isFavorited = !favoritedStocks.contains(symbol);

        if (isFavorited) {
            favoritedStocks.add(symbol);
            fileStorage.addFavoriteStock(symbol);
        } else {
            favoritedStocks.remove(symbol);
            fileStorage.removeFavoriteStock(symbol);
        }

        favoriteStockPresenter.presentFavoriteToggled(symbol, isFavorited);
    }

    /**
     * Retrieves all favorited stocks from file storage and presents them through the output boundary.
     * Creates a defensive copy of the favorited stocks set to prevent external modification.
     */
    @Override
    public void getFavorites() {
        // Refresh from file storage in case of external changes
        favoritedStocks.clear();
        favoritedStocks.addAll(fileStorage.loadFavoriteStocks());
        favoriteStockPresenter.presentFavorites(new HashSet<>(favoritedStocks));
    }
}