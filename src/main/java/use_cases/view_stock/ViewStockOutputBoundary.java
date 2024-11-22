package use_cases.view_stock;

/**
 * Output boundary for viewing stock use case.
 */
public interface ViewStockOutputBoundary {
    /**
     * Display relevant data for a stock.
     * @param viewStockOutputData the output data
     */
    void displayStock(ViewStockOutputData viewStockOutputData);

    /**
     * Display error.
     * @param error the error name
     */
    void error(String error);
}
