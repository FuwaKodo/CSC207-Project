package use_cases.search;

/**
 * Output boundary for search use case.
 */
public interface SearchOutputBoundary {
    /**
     * Display search result.
     * @param searchOutputData the output data
     */
    void displayResult(SearchOutputData searchOutputData);
}
