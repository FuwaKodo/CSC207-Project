package use_cases.loading_hub;

/**
 * Output Data for the Loading Hub Use Case.
 */
public class LoadingHubOutputData {

    private final boolean useCaseFailed;

    public LoadingHubOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
