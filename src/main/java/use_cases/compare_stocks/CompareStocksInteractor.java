package main.java.use_cases.compare_stocks;

import main.java.entities.Stock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The use case to compare stocks using earnings per share, dividends, and
 * the growth percentage.
 */
public class CompareStocksInteractor implements CompareStocksInputBoundary {
    private final CompareStocksOutputBoundary presenter;

    public CompareStocksInteractor(CompareStocksOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(CompareStocksInputData inputData) {
        final String summary = getComparisonSummary(
                inputData.getFirstStock(),
                inputData.getSecondStock(),
                inputData.getStartDate(),
                inputData.getEndDate()
                );
        presenter.displayComparisonSummary(summary);
    }

    private String getComparisonSummary(Stock stock1, Stock stock2, LocalDate start, LocalDate end) {
        // Compare earnings per share
        final Double stock1EPS = stock1.getEarningsPerShare(start, end);
        final Double stock2EPS = stock2.getEarningsPerShare(start, end);
        // Compare growth percentage
        final Double stock1Growth = stock1.getGrowthPercentage(start, end);
        final Double stock2Growth = stock2.getGrowthPercentage(start, end);
        //Compare dividends
        final Double stock1Dividends = stock1.getDividendsPerShare(end);
        final Double stock2Dividends = stock2.getDividendsPerShare(end);

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

    private String formattedDateString(LocalDate date) {
        final String pattern = "dd/MM/yyyy";
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    private String formatSummary(
            String format,
            LocalDate start,
            LocalDate end,
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
