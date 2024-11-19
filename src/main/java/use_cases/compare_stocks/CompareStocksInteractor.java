package main.java.use_cases.compare_stocks;

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
        final Double difference = switch (inputData.getMetricToCompare()) {
            case EARNINGS_PER_SHARE -> compareEarningsPerShare(inputData);
            case DIVIDENDS -> compareDividends(inputData);
            case GROWTH_PERCENTAGE -> compareGrowthPercentage(inputData);
        };

        final CompareStocksOutputData outputData = new CompareStocksOutputData(
                inputData.getFirstStock(),
                inputData.getSecondStock(),
                difference
        );
        presenter.displayResult(outputData);
    }

    private Double compareEarningsPerShare(CompareStocksInputData inputData) {

        return 0.0;
    }

    private Double compareDividends(CompareStocksInputData inputData) {
        return 0.0;
    }

    private Double compareGrowthPercentage(CompareStocksInputData inputData) {
        return 0.0;
    }
}
