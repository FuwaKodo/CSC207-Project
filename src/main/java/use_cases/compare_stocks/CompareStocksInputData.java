package use_cases.compare_stocks;

import java.util.Date;

/**
 * Contains the data inputted into the compare stocks use case when
 * the use case is executed.
 */
public class CompareStocksInputData {
    private final String stock1Name;
    private final String stock2Name;
    private final Date beginDate;
    private final Date endDate;

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
            Date beginDate,
            Date endDate) {
        this.stock1Name = stock1Name;
        this.stock2Name = stock2Name;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getFirstStockSymbol() {
        return stock1Name;
    }

    public String getSecondStockSymbol() {
        return stock2Name;
    }

    public Date getStartDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
