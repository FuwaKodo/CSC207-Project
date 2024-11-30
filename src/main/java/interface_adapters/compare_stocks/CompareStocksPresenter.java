package interface_adapters.compare_stocks;

import interface_adapters.ViewModel;
import use_cases.compare_stocks.CompareStocksOutputBoundary;

public class CompareStocksPresenter implements CompareStocksOutputBoundary {
    private ViewModel<CompareStocksState> viewModel;

    public CompareStocksPresenter(ViewModel<CompareStocksState> viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void displayComparisonSummary(String summary) {
        viewModel.getState().setSummary(summary);
        viewModel.firePropertyChanged();
    }
}
