package interface_adapters.search;

import interface_adapters.ViewManagerModel;
import use_cases.search.SearchOutputBoundary;
import use_cases.search.SearchOutputData;

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
        response.getSymbols().add("Sample 1"); // to be removed
        response.getSymbols().add("Sample 2"); // to be removed
        response.getSymbols().add("Sample 3"); // to be removed
        response.getSymbols().add("Sample 4"); // to be removed
        response.getSymbols().add("Sample 5"); // to be removed
        response.getSymbols().add("Sample 6"); // to be removed
        response.getSymbols().add("Sample 7"); // to be removed
        response.getSymbols().add("Sample 8"); // to be removed
        searchState.setSymbols(response.getSymbols());
        System.out.println(searchState.getSymbols());
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
