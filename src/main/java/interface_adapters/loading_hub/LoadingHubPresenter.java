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
    private final LoadingHubViewModel loadingHubViewModel;
    private final ViewManagerModel viewManagerModel;

    // Constructor
    public LoadingHubPresenter(ViewManagerModel viewManagerModel,
                               LoadingHubViewModel loadingHubViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loadingHubViewModel = loadingHubViewModel;
    }

    /**
     * Returns back to the ViewManager UI.
     * @param responseData the output data
     */
    public void prepareSuccessView(LoadingHubOutputData responseData) {
        /*
        // On success, switch to the ViewStock view.
        final ViewStockState viewStockState = viewStockViewModel.getState();
        viewStockState.setSymbol(responseData.toString());
        this.viewStockViewModel.setState(viewStockState);
        viewStockViewModel.firePropertyChanged();
         */
        final LoadingHubState loadingHubState = loadingHubViewModel.getState();
    }
}
