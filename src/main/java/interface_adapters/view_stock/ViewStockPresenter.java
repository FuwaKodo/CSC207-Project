package interface_adapters.view_stock;

import interface_adapters.ViewManagerModel;
import use_cases.view_stock.ViewStockOutputBoundary;
import use_cases.view_stock.ViewStockOutputData;

/**
 * Presenter for the view stock use case.
 */
public class ViewStockPresenter implements ViewStockOutputBoundary {
    private final ViewStockViewModel stockViewModel;
    private final ViewManagerModel viewManagerModel;

    public ViewStockPresenter(ViewManagerModel viewManagerModel, ViewStockViewModel stockViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.stockViewModel = stockViewModel;
    }

    /**
     * Displays stock.
     * @param response the output data
     */
    public void displayStock(ViewStockOutputData response) {
        final ViewStockState viewStockState = stockViewModel.getState();
        viewStockState.setSymbol(response.getSymbol());
        viewStockState.setCompany(response.getCompany());
        viewStockState.setSharePrices(response.getSharePrices());
        // Optionally uncomment if needed
        // viewStockState.setEarnings(response.getEarnings());

        this.stockViewModel.setState(viewStockState);
        this.stockViewModel.firePropertyChanged();

        this.viewManagerModel.setState(stockViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     * Updates the favorite status of a stock.
     * @param symbol the stock symbol
     * @param isFavorited whether the stock is favorited
     */
    public void presentFavoriteToggled(String symbol, boolean isFavorited) {
        final ViewStockState state = stockViewModel.getState();
        if (isFavorited) {
            state.addFavorite(symbol);
        }
        else {
            state.removeFavorite(symbol);
        }
        stockViewModel.setState(state);
        stockViewModel.firePropertyChanged();
    }

    /**
     * Displays error.
     * @param error the error name
     */
    @Override
    public void error(String error) {
        final ViewStockState viewStockState = stockViewModel.getState();
        viewStockState.setViewStockError(error);
        stockViewModel.setState(viewStockState);
        stockViewModel.firePropertyChanged();
    }
}