package use_cases.loading_hub;

import java.util.Date;

/**
 * Input data for Loading Hub.
 */
public class LoadingHubInputData {
    private final Date startDate;
    private final Date endDate;

    public LoadingHubInputData(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
