package main.java.use_cases.compare_stocks;

import main.java.entities.Stock;

import java.util.Date;

/**
 * Contains the data inputted into the compare stocks use case when
 * the use case is executed.
 */
public class CompareStocksInputData {
    private final Stock stock1;
    private final Stock stock2;
    private final Date beginDate; // The date of beginning of the period of comparison.
    private final Date endDate;   // The date of the end of the period of comparison.
    private final MetricToCompare metricToCompare;

    /**
     * Initialize the compare stock use case input data.
     * @param stock1 the first stock to compare
     * @param stock2 the second stock to compare
     * @param beginDate the beginning of the time period of comparison
     * @param endDate the end of the time period of comparison
     */
    public CompareStocksInputData(
            Stock stock1,
            Stock stock2,
            Date beginDate,
            Date endDate,
            MetricToCompare metricToCompare) {
        this.stock1 = stock1;
        this.stock2 = stock2;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.metricToCompare = metricToCompare;
    }

    public Stock getFirstStock() {
        return stock1;
    }

    public Stock getSecondStock() {
        return stock2;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public MetricToCompare getMetricToCompare() {
        return metricToCompare;
    }

    public enum MetricToCompare {
        EARNINGS_PER_SHARE,
        DIVIDENDS,
        GROWTH_PERCENTAGE
    }
}
