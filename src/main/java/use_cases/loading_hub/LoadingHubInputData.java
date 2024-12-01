package use_cases.loading_hub;

import java.util.Date;

/**
 * Input data for Loading Hub.
 */
public class LoadingHubInputData {
    private final Date startDate;
    private final Date endDate;
    private final String stockSymbol;

    public LoadingHubInputData(String stockSymbol, Date startDate, Date endDate) {
        this.stockSymbol = stockSymbol;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
