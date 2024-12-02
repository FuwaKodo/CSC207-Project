package interface_adapters.compare_stocks;

import interface_adapters.ViewModel;
import use_cases.compare_stocks.CompareStocksOutputBoundary;

/**
 * Presenter for comparing stocks.
 * This class formats the comparison summary and updates the view model
 * with the results of the stock comparison use case.
 */
public class CompareStocksPresenter implements CompareStocksOutputBoundary {
    private ViewModel<CompareStocksState> viewModel;

    /**
     * Constructs a CompareStocksPresenter with the specified view model.
     *
     * @param viewModel the view model used to display the comparison state
     *                  (cannot be null)
     */
    public CompareStocksPresenter(ViewModel<CompareStocksState> viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Displays the comparison summary by updating the view model.
     *
     * @param summary the comparison summary as a string
     *                (can be null if the implementation supports it)
     */
    @Override
    public void displayComparisonSummary(String summary) {
        viewModel.getState().setSummary(summary);
        viewModel.firePropertyChanged();
    }
}
