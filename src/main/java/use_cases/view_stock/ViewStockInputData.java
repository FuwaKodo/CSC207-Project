package use_cases.view_stock;

import java.util.Date;

/**
 * Input data for viewing a stock.
 */
public class ViewStockInputData {
    private final String symbol;
    private final Date startDate;
    private final Date endDate;

    public ViewStockInputData(String symbol, Date startDate, Date endDate) {
        this.symbol = symbol.strip().toUpperCase();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    String getSymbol() {
        return symbol;
    }

    Date getStartDate() {
        return startDate;
    }

    Date getEndDate() {
        return endDate;
    }
}
