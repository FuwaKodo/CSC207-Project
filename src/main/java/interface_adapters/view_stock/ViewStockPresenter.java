package main.java.interface_adapters.view_stock;

import main.java.interface_adapters.ViewManagerModel;
import main.java.use_cases.view_stock.ViewStockOutputBoundary;
import main.java.use_cases.view_stock.ViewStockOutputData;

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
        // On success, display data for the stock selected

        final ViewStockState viewStockState = stockViewModel.getState();
        viewStockState.setSymbol(response.getSymbol());
        viewStockState.setCompany(response.getCompany());
        viewStockState.setSharePrices(response.getSharePrices());
        // viewStockState.setEarnings(response.getEarnings());
        this.stockViewModel.setState(viewStockState);
        this.stockViewModel.firePropertyChanged();

        this.viewManagerModel.setState(stockViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     * Displays error.
     * @param error the error name
     */
    @Override
    public void error(String error) {
        final ViewStockState viewStockState = stockViewModel.getState();
        viewStockState.setViewStockError(error);
        stockViewModel.firePropertyChanged();
    }
}
