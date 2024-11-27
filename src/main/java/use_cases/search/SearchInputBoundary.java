package use_cases.search;

/**
 * Input boundary for the search use case.
 */
public interface SearchInputBoundary {

    /**
     * Executes the use case.
     * @param searchInputData input data for the use case.
     */
    void execute(SearchInputData searchInputData);
}
