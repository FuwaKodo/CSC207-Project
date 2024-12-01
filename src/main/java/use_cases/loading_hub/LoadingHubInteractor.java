package use_cases.loading_hub;

import entities.MetricValues;
import entities.SharePrices;

/**
 * Interactor of the use case. [Logic of the Use Case]
 */
public class LoadingHubInteractor implements LoadingHubInputBoundary {

    private final LoadingHubOutputBoundary loadingHubPresenter;
    private final StockDataInterface loadingHubAccessObject;

    public LoadingHubInteractor(LoadingHubOutputBoundary loadingHubPresenter,
                                StockDataInterface loadingHubAccessObject) {
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

        final SharePrices sharePrices = loadingHubAccessObject.getSharePrices(loadingHubInputData.getStockSymbol(),
                loadingHubInputData.getStartDate(),
                loadingHubInputData.getEndDate());
        final MetricValues volumes = loadingHubAccessObject.getVolumes(loadingHubInputData.getStockSymbol(),
                loadingHubInputData.getStartDate(),
                loadingHubInputData.getEndDate());
        final MetricValues afterHours = loadingHubAccessObject.getAfterHours(loadingHubInputData.getStockSymbol(),
                loadingHubInputData.getStartDate(),
                loadingHubInputData.getEndDate());
        final MetricValues premarkets = loadingHubAccessObject.getPremarkets(loadingHubInputData.getStockSymbol(),
                loadingHubInputData.getStartDate(),
                loadingHubInputData.getEndDate());

        // output data
        final LoadingHubOutputData outputData = new LoadingHubOutputData(loadingHubInputData.getStockSymbol(),
                sharePrices, volumes, afterHours, premarkets);
        loadingHubPresenter.displayResult(outputData);
    }
}
