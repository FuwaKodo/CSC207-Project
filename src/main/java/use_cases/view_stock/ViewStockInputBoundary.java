package main.java.use_cases.view_stock;

/**
 * Input boundary for the view stock use case.
 */
public interface ViewStockInputBoundary {

    /**
     * Executes the use case.
     * @param viewStockInputData input data for the use case.
     */
    void execute(ViewStockInputData viewStockInputData);
}
