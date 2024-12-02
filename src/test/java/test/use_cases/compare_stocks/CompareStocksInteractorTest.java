package test.use_cases.compare_stocks;

import interface_adapters.gateways.StockSymbolsLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.MockMetrics;
import test.MockStockDataLoader;
import use_cases.StockDataInterface;
import use_cases.compare_stocks.CompareStocksInputData;
import use_cases.compare_stocks.CompareStocksInteractor;
import use_cases.compare_stocks.CompareStocksOutputBoundary;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CompareStocksInteractorTest {
    private List<String> companies = List.of("Apple", "Microsoft");

    private StockDataInterface dao;
    private CompareStocksOutputBoundary presenter;
    private String outputSummary;
    private CompareStocksInteractor interactor;

    @BeforeEach
    void setUp() {
        dao = new MockStockDataLoader();
        presenter = summary -> outputSummary = summary;
        interactor = new CompareStocksInteractor(presenter, dao);
    }

    @Test
    void execute() {
        final List<Date> dates = MockMetrics.makeDates();
        final CompareStocksInputData input = new CompareStocksInputData(
                companies.get(0),
                companies.get(1),
                dates.get(0),
                dates.get(1)
        );
        interactor.execute(input);

        assertFalse(outputSummary.isEmpty());
    }

    @Test
    void getStockSymbols() {
        List<String> names = interactor.getStockSymbols();
        StockSymbolsLoader symbolsLoader = new StockSymbolsLoader();
        assertTrue(names.equals(symbolsLoader.getSymbols()));
    }
}
