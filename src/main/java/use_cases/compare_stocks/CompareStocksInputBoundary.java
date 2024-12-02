package use_cases.compare_stocks;

/**
 * Input boundary interface for comparing stocks.
 * This interface defines the contract for the stock comparison use case.
 */
public interface CompareStocksInputBoundary {

    /**
     * Executes the stock comparison use case with the provided input data.
     *
     * @param compareStocksInputData the input data containing stock comparison parameters
     *                               (can be null if the implementation supports it)
     */
    void execute(CompareStocksInputData compareStocksInputData);
}
