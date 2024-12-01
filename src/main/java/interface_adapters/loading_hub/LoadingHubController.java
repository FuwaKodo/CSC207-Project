package interface_adapters.loading_hub;

import java.util.Date;

import use_cases.loading_hub.LoadingHubInputBoundary;
import use_cases.loading_hub.LoadingHubInputData;

/**
 * Controller for the Loading Hub Use Case.
 * [Highest Hierarchy]
 */
public class LoadingHubController {
    private final LoadingHubInputBoundary loadingHubUseCaseInteractor;

    // Constructor
    public LoadingHubController(LoadingHubInputBoundary loadingHubUseCaseInteractor) {
        this.loadingHubUseCaseInteractor = loadingHubUseCaseInteractor;
    }

    /**
     * Executes the loading hub use case.
     * @param stockSymbol company symbol
     * @param startDate the start date
     * @param endDate the end date
     */
    public void execute(String stockSymbol, Date startDate, Date endDate) {
        final LoadingHubInputData loadingHubInputData = new LoadingHubInputData(stockSymbol, startDate, endDate);
        loadingHubUseCaseInteractor.execute(loadingHubInputData);
    }

}
