package main.java.use_cases.compare_stocks;

import main.java.entities.Stock;

import java.time.LocalDate;

/**
 * Contains the data inputted into the compare stocks use case when
 * the use case is executed.
 */
public class CompareStocksInputData {
    private final Stock stock1;
    private final Stock stock2;
    private final LocalDate beginDate; // The date of beginning of the period of comparison.
    private final LocalDate endDate;   // The date of the end of the period of comparison.

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
            LocalDate beginDate,
            LocalDate endDate) {
        this.stock1 = stock1;
        this.stock2 = stock2;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public Stock getFirstStock() {
        return stock1;
    }

    public Stock getSecondStock() {
        return stock2;
    }

    public LocalDate getStartDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
