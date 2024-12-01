package use_cases.loading_hub;

/**
 * Interactor of the use case. [Logic of the Use Case]
 */
public class LoadingHubInteractor implements LoadingHubInputBoundary {

    private final LoadingHubOutputBoundary loadingHubPresenter;
    private final LoadingHubAccessInterface loadingHubAccessObject;

    public LoadingHubInteractor(LoadingHubOutputBoundary loadingHubPresenter,
                                LoadingHubAccessInterface loadingHubAccessObject) {
        this.loadingHubPresenter = loadingHubPresenter;
        this.loadingHubAccessObject = loadingHubAccessObject;
    }

    /**
     * Execute the use case.
     * @param loadingHubInputData the input data
     */
    @Override
    public void execute(LoadingHubInputData loadingHubInputData) {
        // TODO: Logical Error here!
        // loadingHubPresenter.displayResult();
    }
}
