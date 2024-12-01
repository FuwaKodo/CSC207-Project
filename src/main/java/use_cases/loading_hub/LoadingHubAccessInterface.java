package use_cases.loading_hub;

import java.util.Date;

import entities.SharePrices;

/**
 * Data access interface for the use case.
 */
public interface LoadingHubAccessInterface {
    /**
     * Retrieves the sharePrices to display data in the graph.
     * @param startDate the start date
     * @param endDate the end date
     * @return returns the sharePrices given a start and end date
     */
    SharePrices getSharePrices(Date startDate, Date endDate);
}
