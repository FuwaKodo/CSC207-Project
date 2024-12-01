package interface_adapters.text_analyze_stock;

import entities.SharePrices;
import interface_adapters.gateways.StockDataLoader;
import use_cases.StockDataInterface;
import use_cases.text_analyze_stock.AnalyzeStock;
import use_cases.text_analyze_stock.StockAnalysisResult;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Presenter for the stock analysis use case.
 */
public class StockPresenterAlt {

    private final StockViewModel viewModel;

    public StockPresenterAlt(StockViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Performs stock analysis and updates the ViewModel with the results.
     *
     * @param stockSymbol the stock symbol
     * @param startDate   the initial date for stock data
     * @param endDate     the current date for stock data
     */
    public void analyzeStock(String stockSymbol, Date startDate, Date endDate) {
        final StockDataInterface stockDataLoader = new StockDataLoader();

        // Fetch stock data
        final SharePrices startPrices = stockDataLoader.getSharePrice(stockSymbol, startDate);
        final SharePrices currentPrices = stockDataLoader.getSharePrice(stockSymbol, endDate);

        // Perform analysis
        final StockAnalysisResult result = AnalyzeStock.calculateProjectedPrice(
                stockSymbol,
                currentPrices.getHighPrices().get(0),
                startPrices.getHighPrices().get(0)
        );

        // Update ViewModel
        viewModel.setStockName(result.getStockName());
        viewModel.setCurrentPrice(result.getCurrentPrice());
        viewModel.setProjectedPrices(result.getProjectedPrice1(), result.getProjectedPrice2(), result.getProjectedPrice3());
        viewModel.setAction(result.getAction());
    }
}
