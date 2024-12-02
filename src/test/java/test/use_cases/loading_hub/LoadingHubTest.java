package test.use_cases.loading_hub;

import entities.Stock;
import interface_adapters.gateways.StockDataLoader;
import interface_adapters.loading_hub.LoadingHubPresenter;
import org.junit.Test;
import use_cases.StockDataInterface;
import use_cases.SymbolNameDataAccessInterface;
import use_cases.loading_hub.LoadingHubOutputBoundary;
import use_cases.loading_hub.LoadingHubOutputData;

public class LoadingHubTest {
    final private StockDataInterface dataAccessObject = new StockDataLoader();

    @Test
    public void executeEmptyTest() {
        LoadingHubOutputBoundary loadingHubPresenter = new LoadingHubOutputBoundary() {
            @Override
            public void displayResult(LoadingHubOutputData outputData) {
                assert(outputData != null);
            }
        };
    }
}
