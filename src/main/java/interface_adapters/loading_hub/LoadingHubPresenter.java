package interface_adapters.loading_hub;

import interface_adapters.ViewManagerModel;
import interface_adapters.view_stock.ViewStockState;
import interface_adapters.view_stock.ViewStockViewModel;
import use_cases.loading_hub.LoadingHubOutputBoundary;
import use_cases.loading_hub.LoadingHubOutputData;

/**
 * The Presenter for the Loading Hub.
 */
public class LoadingHubPresenter implements LoadingHubOutputBoundary {
    private final ViewStockViewModel loadingHubViewModel;
    private final ViewManagerModel viewManagerModel;

    // Constructor
    public LoadingHubPresenter(ViewManagerModel viewManagerModel,
                               ViewStockViewModel loadingHubViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loadingHubViewModel = loadingHubViewModel;
    }

    /**
     * Returns back to the ViewManager UI.
     * @param outputData the output data
     */
    @Override
    public void displayResult(LoadingHubOutputData outputData) {
        final ViewStockState loadingHubState = loadingHubViewModel.getState();
        loadingHubState.setSymbol(outputData.getStockSymbol());
        loadingHubState.setSharePrices(outputData.getSharePrices());
        this.loadingHubViewModel.setState(loadingHubState);
        this.loadingHubViewModel.firePropertyChanged();

        this.viewManagerModel.setState(loadingHubViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }
}
