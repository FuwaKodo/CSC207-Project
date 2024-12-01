package interface_adapters.favoritesIA;

import java.util.Set;

import use_cases.favorites.FavoriteStockOutputBoundary;

/**
 * Presenter class that handles the presentation logic for favorite stock operations.
 * Implements the FavoriteStockOutputBoundary interface to receive updates from the interactor.
 */
public class FavoriteStockPresenter implements FavoriteStockOutputBoundary {
    private FavoriteStockViewModel viewModel;

    /**
     * Constructs a new FavoriteStockPresenter.
     *
     * @param viewModel The view model to be updated with favorite stock changes
     */
    public FavoriteStockPresenter(FavoriteStockViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Updates the view model when a stock's favorite status is toggled.
     *
     * @param symbol The stock symbol that was toggled
     * @param isFavorited The new favorite status of the stock
     */
    @Override
    public void presentFavoriteToggled(String symbol, boolean isFavorited) {
        final FavoriteStockState state = viewModel.getState();
        if (isFavorited) {
            state.addFavorite(symbol);
        }
        else {
            state.removeFavorite(symbol);
        }
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    /**
     * Updates the view model with a complete set of favorite stocks.
     *
     * @param favorites The set of all favorited stock symbols
     */
    @Override
    public void presentFavorites(Set<String> favorites) {
        final FavoriteStockState state = new FavoriteStockState();
        for (String symbol : favorites) {
            state.addFavorite(symbol);
        }
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
