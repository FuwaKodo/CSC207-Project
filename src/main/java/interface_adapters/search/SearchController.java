package main.java.interface_adapters.search;

import main.java.use_cases.search.SearchInputBoundary;
import main.java.use_cases.search.SearchInputData;

/**
 * Controller for the search use case.
 */
public class SearchController {

    private final SearchInputBoundary searchUseCaseInteractor;

    public SearchController(SearchInputBoundary searchUseCaseInteractor) {
        this.searchUseCaseInteractor = searchUseCaseInteractor;
    }

    /**
     * Executes the search use case.
     * @param input input string
     */
    public void execute(String input) {
        final SearchInputData searchInputData = new SearchInputData(input);

        searchUseCaseInteractor.execute(searchInputData);
    }
}
