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
    private final ViewStockViewModel viewStockViewModel;
    private final ViewManagerModel viewManagerModel;

    // Constructor
    public LoadingHubPresenter(ViewManagerModel viewManagerModel,
                               LoadingHubViewModel loadingHubViewModel,
                               ViewStockViewModel viewStockViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loadingHubViewModel = loadingHubViewModel;
        this.viewStockViewModel = viewStockViewModel;
    }

    @Override
    public void prepareSuccessView(LoadingHubOutputData responseData) {
        // On success, switch to the ViewStock view.
        final ViewStockState viewStockState = viewStockViewModel.getState();
        viewStockState.setSymbol(responseData.toString());
        this.viewStockViewModel.setState(viewStockState);
        viewStockViewModel.firePropertyChanged();

        viewManagerModel.setState(viewManagerModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(SignupOutputData outputData) {

    }
}
