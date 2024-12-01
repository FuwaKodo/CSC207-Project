package interface_adapters.loading_hub;

import interface_adapters.ViewManagerModel;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchViewModel;
import use_cases.loading_hub.LoadingHubAccessInterface;
import use_cases.loading_hub.LoadingHubInputBoundary;
import use_cases.loading_hub.LoadingHubInteractor;
import use_cases.loading_hub.LoadingHubOutputBoundary;
import use_cases.search.SearchDataAccessInterface;
import use_cases.search.SearchInputBoundary;
import use_cases.search.SearchInteractor;
import use_cases.search.SearchOutputBoundary;

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
     * @return A configured LoadingHubController ready for use
     */
    public static LoadingHubController create(
            ViewManagerModel viewManagerModel,
            LoadingHubViewModel loadingHubViewModel,
            LoadingHubAccessInterface dataAccessObject) {

        // Create the presenter with its required dependencies
        final LoadingHubOutputBoundary loadingHubPresenter =
                new LoadingHubPresenter(viewManagerModel, loadingHubViewModel);

        // Create the interactor with its dependencies
        final LoadingHubInputBoundary loadingHubInteractor =
                new LoadingHubInteractor(loadingHubPresenter, dataAccessObject);

        // Create and return the controller
        return new LoadingHubController(loadingHubInteractor);
    }
}
