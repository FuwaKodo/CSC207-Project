package use_cases.compare_stocks;

/**
 * Output boundary interface for comparing stocks.
 * This interface defines how the results of the stock comparison use case
 * are presented to the user or the system.
 */
public interface CompareStocksOutputBoundary {

    /**
     * Displays the comparison summary of stocks.
     *
     * @param summary the comparison summary as a string
     *                (can be null if the implementation supports it)
     */
    void displayComparisonSummary(String summary);
}
