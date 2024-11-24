package main.java.interface_adapters.search;

import main.java.interface_adapters.ViewManagerModel;
import main.java.use_cases.search.SearchOutputBoundary;
import main.java.use_cases.search.SearchOutputData;

/**
 * Presenter for the search use case.
 */
public class SearchPresenter implements SearchOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(ViewManagerModel viewManagerModel, SearchViewModel searchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
    }

    /**
     * Displays search result.
     * @param response the output data
     */
    public void displayResult(SearchOutputData response) {
        // On success, create a new window containing search result

        final SearchState searchState = searchViewModel.getState();
        searchState.setInput(response.getInput());
        searchState.setSymbols(response.getSymbols());
        this.searchViewModel.setState(searchState);
        this.searchViewModel.firePropertyChanged();

        this.viewManagerModel.setState(searchViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     * Displays error.
     * @param error the error name
     */
    @Override
    public void error(String error) {
        final SearchState searchState = searchViewModel.getState();
        searchState.setSearchError(error);
        searchViewModel.firePropertyChanged();
    }
}
