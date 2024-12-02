package use_cases.compare_stocks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entities.SharePrices;
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

    public List<String> getStockSymbols() {
        return symbolsLoader.getSymbols();
    }

    private String getComparisonSummary(String symbol1, String symbol2, Date start, Date end) {
        if (start.after(end)) {
            return "The end date must be after the start date!";
        }

        // Compare stock volumes
        final Double stock1Volumes = dataAccess.getVolume(symbol1, end);
        final Double stock2Volumes = dataAccess.getVolume(symbol2, end);
        // Compare growth percentages
        final SharePrices stock1SharePrices = dataAccess.getSharePrices(symbol1, start, end);
        final SharePrices stock2SharePrices = dataAccess.getSharePrices(symbol2, start, end);

        final String volumesSummary = String.format(
                "At the end of %s, the volume of %s is %.1f shares and the volume of %s is %.1f shares.",
                formattedDateString(end),
                symbol1, stock1Volumes,
                symbol2, stock2Volumes
        );

        final String growthSummary = String.format(
                "From %s to %s, %s changed from $%.1f per share to $%.1f per share and " +
                        "%s changed from $%.1f per share to $%.1f per share.",
                formattedDateString(start), formattedDateString(end),
                symbol1, stock1SharePrices.getValue(start), stock1SharePrices.getValue(end),
                symbol2, stock2SharePrices.getValue(start), stock2SharePrices.getValue(end)
        );

        return volumesSummary + "\n" + growthSummary;
    }

    private String formattedDateString(Date date) {
        final String pattern = "dd/MM/yyyy";
        final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
}
