package interface_adapters.compare_stocks;

import java.util.List;

import use_cases.compare_stocks.CompareStocksInputData;
import use_cases.compare_stocks.CompareStocksInteractor;

public class CompareStocksController {
    private final CompareStocksInteractor interactor;

    public CompareStocksController(CompareStocksInteractor interactor) {
        this.interactor = interactor;
    }

    public void execute(CompareStocksInputData inputData) {
        interactor.execute(inputData);
    }

    public List<String> getStockNames() {
        return interactor.getStockNames();
    }
}
