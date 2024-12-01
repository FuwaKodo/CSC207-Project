package use_cases.compare_stocks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entities.SharePrices;
import entities.Stock;
import interface_adapters.gateways.StockSymbolsLoader;
import use_cases.StockDataInterface;

/**
 * The use case to compare stocks using earnings per share, dividends, and
 * the growth percentage.
 */
public class CompareStocksInteractor implements CompareStocksInputBoundary {
    private final CompareStocksOutputBoundary presenter;
    private final StockDataInterface dataAccess;
    private final StockSymbolsLoader symbolsLoader;

    public CompareStocksInteractor(CompareStocksOutputBoundary presenter, StockDataInterface dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
        symbolsLoader = new StockSymbolsLoader();
    }

    @Override
    public void execute(CompareStocksInputData inputData) {
        final String summary = getComparisonSummary(
                inputData.getFirstStockSymbol(),
                inputData.getSecondStockSymbol(),
                inputData.getStartDate(),
                inputData.getEndDate()
                );
        presenter.displayComparisonSummary(summary);
    }

    public List<String> getStockNames() {
        return symbolsLoader.getSymbols();
    }

    private String getComparisonSummary(String symbol1, String symbol2, Date start, Date end) {
        // Compare stock volumes
        final Double stock1Volumes = dataAccess.getVolume(symbol1, end);
        final Double stock2Volumes = dataAccess.getVolume(symbol2, end);
        // Compare growth percentages
        final SharePrices stock1SharePrices = dataAccess.getSharePrices(symbol1, start, end);
        final SharePrices stock2SharePrices = dataAccess.getSharePrices(symbol2, start, end);

        final String epsSummary = formatSummary(
                "From %s to %s, %s earned $%.1f earnings per share while %s earned $%.1f earnings per share.",
                start, end, stock1, stock2, stock1EPS, stock2EPS
        );

        final String growthSummary = formatSummary(
                "From %s to %s, %s grew %.1f%% while %s grew %.1f%%.",
                start, end, stock1, stock2, stock1Growth, stock2Growth
        );

        final String dividendsSummary = String.format(
                "On %s, %s featured %.1f dividends per share while %s featured %.1f per share.",
                formattedDateString(end),
                stock1.getCompany(),
                stock1Dividends,
                stock2.getCompany(),
                stock2Dividends
        );

        return epsSummary + "\n" + growthSummary + "\n" + dividendsSummary;
    }

    private String formattedDateString(Date date) {
        final String pattern = "dd/MM/yyyy";
        final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    private String formatSummary(
            String format,
            Date start,
            Date end,
            Stock stock1,
            Stock stock2,
            Double firstStockVal,
            Double secondStockVal) {
        return String.format(
                format,
                formattedDateString(start),
                formattedDateString(end),
                stock1.getCompany(),
                firstStockVal,
                stock2.getCompany(),
                secondStockVal
        );
    }
}
