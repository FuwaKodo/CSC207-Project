package interface_adapters.compare_stocks;

import java.util.List;

import use_cases.compare_stocks.CompareStocksInputData;
import use_cases.compare_stocks.CompareStocksInteractor;

/**
 * Controller class for comparing stocks.
 * This class acts as an intermediary between the view and the interactor
 * for the stock comparison use case.
 */
public class CompareStocksController {
    private final CompareStocksInteractor interactor;

    /**
     * Constructs a CompareStocksController with the given interactor.
     *
     * @param interactor the interactor responsible for handling stock comparison logic
     */
    public CompareStocksController(CompareStocksInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the stock comparison use case with the provided input data.
     *
     * @param inputData the input data containing stock comparison parameters
     */
    public void execute(CompareStocksInputData inputData) {
        interactor.execute(inputData);
    }

    /**
     * Retrieves the names (symbols) of the stocks being compared.
     *
     * @return a list of stock symbols being compared
     */
    public List<String> getStockNames() {
        return interactor.getStockSymbols();
    }
}
