package interface_adapters.text_analyze_stock;

import use_cases.text_analyze_stock.FutureStockOutputBoundary;
import use_cases.text_analyze_stock.StockAnalysisResult;

public class StockPresenterNew implements FutureStockOutputBoundary {
    private final StockViewModel viewModel;

    public StockPresenterNew(StockViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Override
    public void displayResult(StockAnalysisResult result) {
        viewModel.setStockName(result.getStockName());
        viewModel.setCurrentPrice(result.getCurrentPrice());
        viewModel.setProjectedPrices(result.getProjectedPrice1(), result.getProjectedPrice2(),
                result.getProjectedPrice3());
        viewModel.setAction(result.getAction());

    }
}
