package interface_adapters.loading_hub;

import use_cases.loading_hub.LoadingHubInputBoundary;

/**
 * Controller for the Loading Hub Use Case.
 */
public class LoadingHubController {
    private final LoadingHubInputBoundary loadingHubUseCaseInteractor;

    // Constructor
    public LoadingHubController(LoadingHubInputBoundary loadingHubUseCaseInteractor) {
        this.loadingHubUseCaseInteractor = loadingHubUseCaseInteractor;
    }

}
