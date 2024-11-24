package main.java.interface_adapters.search;

import main.java.Constants;
import main.java.interface_adapters.ViewModel;

/**
 * View model for search use case.
 */
public class SearchViewModel extends ViewModel<SearchState> {

    public SearchViewModel() {
        super(Constants.SEARCH_VIEW);
        setState(new SearchState());
    }
}
