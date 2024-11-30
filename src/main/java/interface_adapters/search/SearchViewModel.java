package interface_adapters.search;

import app.Constants;
import interface_adapters.ViewModel;

/**
 * View model for search use case.
 */
public class SearchViewModel extends ViewModel<SearchState> {

    public SearchViewModel() {
        super(Constants.SEARCH_VIEW);
        setState(new SearchState());
    }
}
