package use_cases.view_stock;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import app.Constants;
import entities.SharePrices;
import use_cases.StockDataInterface;
import use_cases.SymbolNameDataAccessInterface;

/**
 * Interactor of the use case.
 */
public class ViewStockInteractor implements ViewStockInputBoundary {

    private final ViewStockOutputBoundary viewStockPresenter;
    private final StockDataInterface viewStockDataAccessObject;
    private final SymbolNameDataAccessInterface symbolNameDataAccessObject;

    public ViewStockInteractor(ViewStockOutputBoundary viewStockPresenter,
                               StockDataInterface stockDataLoader,
                               SymbolNameDataAccessInterface symbolDataAccessObject) {
        this.viewStockPresenter = viewStockPresenter;
        this.viewStockDataAccessObject = stockDataLoader;
        this.symbolNameDataAccessObject = symbolDataAccessObject;
    }

    /**
     * Executes the use case.
     *
     * @param inputData input data for the use case.
     */
    @Override
    public void execute(ViewStockInputData inputData) {
        final String symbol = inputData.getSymbol();
        final String company = symbolNameDataAccessObject.getCompany(symbol);
        final SharePrices sharePrices = viewStockDataAccessObject.getSharePrices(symbol,
                toDate(LocalDate.now().minusDays(Constants.FIVE_YEARS)), toDate(LocalDate.now()));
        /*
        final Stock stock = new Stock(viewStockDataAccessObject, symbol, company);
        final List<Double> sharePrices1 = new ArrayList<>();

        final LocalDate fiveYearsBefore = LocalDate.now().minusDays(Constants.FIVE_YEARS);
        for (LocalDate date = fiveYearsBefore; !date.isAfter(LocalDate.now()); date = date.plusDays(1)) {
            sharePrices1.add(stock.getSharePrice(toDate(date)));
        }
        */
        final ViewStockOutputData viewStockOutputData = new ViewStockOutputData(company, symbol, sharePrices);

        viewStockPresenter.displayStock(viewStockOutputData);
    }

    private static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
