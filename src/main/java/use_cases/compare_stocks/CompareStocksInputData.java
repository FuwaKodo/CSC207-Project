package use_cases.compare_stocks;

import java.time.LocalDate;

/**
 * Contains the data inputted into the compare stocks use case when
 * the use case is executed.
 */
public class CompareStocksInputData {
    private final String stock1Name;
    private final String stock2Name;
    private final LocalDate beginDate; // The date of beginning of the period of comparison.
    private final LocalDate endDate;   // The date of the end of the period of comparison.

    /**
     * Initialize the compare stock use case input data.
     * @param stock1Name name of the first stock to compare
     * @param stock2Name name of the second stock to compare
     * @param beginDate the beginning of the time period of comparison
     * @param endDate the end of the time period of comparison
     */
    public CompareStocksInputData(
            String stock1Name,
            String stock2Name,
            LocalDate beginDate,
            LocalDate endDate) {
        this.stock1Name = stock1Name;
        this.stock2Name = stock2Name;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getFirstStockName() {
        return stock1Name;
    }

    public String getSecondStockName() {
        return stock2Name;
    }

    public LocalDate getStartDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
