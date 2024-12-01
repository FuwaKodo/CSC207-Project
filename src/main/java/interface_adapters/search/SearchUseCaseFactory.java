package interface_adapters.search;

import interface_adapters.ViewManagerModel;
import use_cases.SymbolNameDataAccessInterface;
import use_cases.search.SearchInputBoundary;
import use_cases.search.SearchInteractor;
import use_cases.search.SearchOutputBoundary;

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
     * @param searchViewModel The view model for search use case
     * @param dataAccessObject The concrete implementation of ViewStockDataAccessInterface to use
     * @return A configured ViewStockController ready for use
     */
    public static SearchController create(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            SymbolNameDataAccessInterface dataAccessObject) {

        // Create the presenter with its required dependencies
        final SearchOutputBoundary searchPresenter =
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
