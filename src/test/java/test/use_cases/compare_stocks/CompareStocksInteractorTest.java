package test.use_cases.compare_stocks;

import entities.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.MockMetrics;
import test.MockStockDataLoader;
import use_cases.StockDataAccessInterface;
import use_cases.compare_stocks.CompareStocksInputData;
import use_cases.compare_stocks.CompareStocksInteractor;
import use_cases.compare_stocks.CompareStocksOutputBoundary;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CompareStocksInteractorTest {
    private List<String> companies = List.of("Apple", "Microsoft");

    private StockDataAccessInterface dao;
    private CompareStocksOutputBoundary presenter;
    private String outputSummary;
    private CompareStocksInteractor interactor;

    @BeforeEach
    void setUp() {
        dao = new StockDataAccessInterface() {
            @Override
            public List<String> getAllCompanyNames() {
                return companies;
            }

            @Override
            public Stock getStockByCompany(String company) {
                // Symbol irrelevant here
                return new Stock(new MockStockDataLoader(company, "GENERIC_SYMBOL"));
            }
        };

        presenter = new CompareStocksOutputBoundary() {
            @Override
            public void displayComparisonSummary(String summary) {
                outputSummary = summary;
            }
        };

        interactor = new CompareStocksInteractor(presenter, dao);
    }

    @Test
    void execute() {
        final List<LocalDate> dates = MockMetrics.makeDates();
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
    void getStockNames() {
        List<String> names = interactor.getStockNames();
        assertTrue(names.equals(companies));
    }
}
