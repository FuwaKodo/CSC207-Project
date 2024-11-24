package main.java.use_cases.search;

/**
 * Output boundary for search use case.
 */
public interface SearchOutputBoundary {
    /**
     * Display search result.
     * @param searchOutputData the output data
     */
    void displayResult(SearchOutputData searchOutputData);

    /**
     * Display error.
     * @param error the error name
     */
    void error(String error);
}
