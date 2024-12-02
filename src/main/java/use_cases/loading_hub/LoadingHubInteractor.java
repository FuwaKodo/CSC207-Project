package use_cases.loading_hub;

import entities.MetricValues;
import entities.SharePrices;
import use_cases.StockDataInterface;
import use_cases.SymbolNameDataAccessInterface;

/**
 * Interactor of the use case. [Logic of the Use Case]
 */
public class LoadingHubInteractor implements LoadingHubInputBoundary {
    private final LoadingHubOutputBoundary loadingHubPresenter;
    private final StockDataInterface loadingHubAccessObject;
    private final SymbolNameDataAccessInterface symbolNameDataAccess;

    public LoadingHubInteractor(LoadingHubOutputBoundary loadingHubPresenter,
                                StockDataInterface loadingHubAccessObject,
                                SymbolNameDataAccessInterface symbolNameDataAccessInterface) {
        this.loadingHubPresenter = loadingHubPresenter;
        this.loadingHubAccessObject = loadingHubAccessObject;
        this.symbolNameDataAccess = symbolNameDataAccessInterface;
    }

    /**
     * Execute the use case.
     * @param loadingHubInputData the input data
     */
    @Override
    public void execute(LoadingHubInputData loadingHubInputData) {
        final String companyName = symbolNameDataAccess.getCompany(loadingHubInputData.getStockSymbol());

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
                companyName, sharePrices, volumes, afterHours, premarkets);
        loadingHubPresenter.displayResult(outputData);
    }
}
