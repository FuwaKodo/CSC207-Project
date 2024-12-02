package interface_adapters.text_analyze_stock;

import use_cases.StockDataInterface;
import use_cases.text_analyze_stock.FutureStockInputBoundary;
import use_cases.text_analyze_stock.FutureStockOutputBoundary;
import use_cases.text_analyze_stock.StockInteractor;

/**
 *  Factory class for creating instances of {@link StockController}.
 */
public class StockControllerFactory {
    /**
     * The Stock Controller Factory.
     * @param viewModel The view Model.
     * @param dataLoader The data Loader.
     * @return Returns the value.
     */
    public static StockController createStockController(StockViewModel viewModel,
                                                        StockDataInterface dataLoader) {
        final FutureStockOutputBoundary stockPresenter = new StockPresenterNew(viewModel);
        final FutureStockInputBoundary stockInteractor = new StockInteractor(stockPresenter, dataLoader);
        return new StockController(stockInteractor);
    }
}
