package main.java.use_cases.search;

import main.java.interface_adapters.ViewManagerModel;
import main.java.interface_adapters.search.SearchController;
import main.java.interface_adapters.search.SearchPresenter;
import main.java.interface_adapters.search.SearchViewModel;

/**
 * Factory class responsible for creating and assembling all components needed for the search stock use case.
 * This includes creating the interactor, presenter, view model, and data access objects
 */
public class SearchUseCaseFactory {

    /**
     * Creates all components for the search use case and returns a configured controller.
     * This method wires up all the dependencies following clean architecture principles.
     *
     * @param viewManagerModel The view manager model that handles view switching
     * @param dataAccessObject The concrete implementation of ViewStockDataAccessInterface to use
     * @return A configured ViewStockController ready for use
     */
    public static SearchController create(
            ViewManagerModel viewManagerModel,
            SearchDataAccessInterface dataAccessObject) {

        // Create the view model and state
        final SearchViewModel searchViewModel = new SearchViewModel();

        // Create the presenter with its required dependencies
        final SearchPresenter searchPresenter =
                new SearchPresenter(viewManagerModel, searchViewModel);

        // Create the interactor with its dependencies
        final SearchInputBoundary searchInteractor = new SearchInteractor(
                searchPresenter,
                dataAccessObject
        );

        // Create and return the controller
        return new SearchController(searchInteractor);
    }
}
