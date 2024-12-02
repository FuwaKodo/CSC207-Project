package interface_adapters.loading_hub;

import interface_adapters.ViewManagerModel;
import interface_adapters.view_stock.ViewStockViewModel;
import use_cases.StockDataInterface;
import use_cases.SymbolNameDataAccessInterface;
import use_cases.loading_hub.LoadingHubInputBoundary;
import use_cases.loading_hub.LoadingHubInteractor;
import use_cases.loading_hub.LoadingHubOutputBoundary;

/**
 * Factory class responsible for creating and assembling all components needed for the LoadingHub use case.
 * This includes creating the interactor, presenter, view model, and data access objects
 */
public class LoadingHubUseCaseFactory {
    /**
     * Creates all components for the loading hub use case and returns a configured controller.
     * This method wires up all the dependencies following clean architecture principles.
     *
     * @param viewManagerModel The view manager model that handles view switching
     * @param loadingHubViewModel The view model for loading hub use case
     * @param dataAccessObject The concrete implementation of LoadingHubAccessInterface to use
     * @param symbolNameDataAccessInterface The concrete implementation of finding symbol
     * @return A configured LoadingHubController ready for use
     */
    public static LoadingHubController create(
            ViewManagerModel viewManagerModel,
            ViewStockViewModel loadingHubViewModel,
            StockDataInterface dataAccessObject,
            SymbolNameDataAccessInterface symbolNameDataAccessInterface) {

        // Create the presenter with its required dependencies
        final LoadingHubOutputBoundary loadingHubPresenter =
                new LoadingHubPresenter(viewManagerModel, loadingHubViewModel);

        // Create the interactor with its dependencies
        final LoadingHubInputBoundary loadingHubInteractor =
                new LoadingHubInteractor(loadingHubPresenter, dataAccessObject, symbolNameDataAccessInterface);

        // Create and return the controller
        return new LoadingHubController(loadingHubInteractor);
    }
}
