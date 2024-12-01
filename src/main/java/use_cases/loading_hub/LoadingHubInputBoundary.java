package use_cases.loading_hub;

/**
 * Input Boundary for actions which are related to preloading data.
 */
public interface LoadingHubInputBoundary {
    /**
     * Executes the loading hub use case.
     * @param loadingHubInputData the input data
     */
    void execute(LoadingHubInputData loadingHubInputData);
}
