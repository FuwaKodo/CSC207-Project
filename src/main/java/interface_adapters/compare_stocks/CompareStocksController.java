package interface_adapters.compare_stocks;

import entities.Stock;
import use_cases.compare_stocks.CompareStocksInputBoundary;
import use_cases.compare_stocks.CompareStocksInputData;

import java.time.LocalDate;

public class CompareStocksController {
    private final CompareStocksInputBoundary useCaseInteractor;

    public CompareStocksController(CompareStocksInputBoundary useCaseInteractor) {
        this.useCaseInteractor = useCaseInteractor;
    }

    public void execute(Stock stock1, Stock stock2, LocalDate start, LocalDate end) {
        final CompareStocksInputData inputData = new CompareStocksInputData(stock1, stock2, start, end);
        useCaseInteractor.execute(inputData);
    }
}
