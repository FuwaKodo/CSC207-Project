package use_cases.loading_hub;

/**
 * The output boundary for the Loading Hub Use Case.
 */
public interface LoadingHubOutputBoundary {
    /**
     * Prepares the success view for the Signup Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(SignupOutputData outputData);
}
