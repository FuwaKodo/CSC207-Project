package ui.compare_stocks;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;

import interface_adapters.ViewModel;
import interface_adapters.compare_stocks.CompareStocksController;
import interface_adapters.compare_stocks.CompareStocksPresenter;
import interface_adapters.compare_stocks.CompareStocksState;
import interface_adapters.gateways.StockDataLoader;
import use_cases.StockDataInterface;
import use_cases.compare_stocks.CompareStocksInteractor;

/**
 * Utility class for displaying the Compare Stocks dialog.
 * @author Your Name
 * @version 1.0
 * @since 1.0
 */
public final class CompareStocksViewDisplayer {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;

    private CompareStocksViewDisplayer() {

    }

    /**
     * Shows the Compare Stocks dialog.
     * @param parentFrame the parent frame for the dialog
     */
    public static void showDialog(JFrame parentFrame) {
        final ViewModel<CompareStocksState> viewModel = new ViewModel<>("Compare Stocks");
        viewModel.setState(new CompareStocksState());
        final CompareStocksController controller = makeController(viewModel);
        final CompareStocksView view = new CompareStocksView(controller, viewModel);

        final JDialog dialog = new JDialog(parentFrame, "Compare Stocks", true);
        dialog.setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        dialog.getContentPane().add(view);
        dialog.pack();
        dialog.setVisible(true);
    }

    /**
     * Creates a new CompareStocksController instance.
     * @param viewModel the view model for the controller
     * @return a new CompareStocksController instance
     */
    private static CompareStocksController makeController(
            ViewModel<CompareStocksState> viewModel) {
        final CompareStocksPresenter presenter = new CompareStocksPresenter(viewModel);
        final StockDataInterface dai = new StockDataLoader();
        final CompareStocksInteractor interactor = new CompareStocksInteractor(presenter, dai);
        return new CompareStocksController(interactor);
    }
}
