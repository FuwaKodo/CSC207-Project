package ui.compare_stocks;

import interface_adapters.ViewModel;
import interface_adapters.compare_stocks.CompareStocksController;
import interface_adapters.compare_stocks.CompareStocksPresenter;
import interface_adapters.compare_stocks.CompareStocksState;
import interface_adapters.gateways.StockDataLoader;
import use_cases.StockDataInterface;
import use_cases.compare_stocks.CompareStocksInteractor;

import javax.swing.*;

public class CompareStocksViewDisplayer {
    private CompareStocksViewDisplayer() {}

    public static void showDialog(JFrame parentFrame) {
        final ViewModel<CompareStocksState> vm = new ViewModel<>("Compare Stocks");
        final CompareStocksController controller = makeController(vm);
        final CompareStocksView view = new CompareStocksView(controller, vm);

        final JDialog dialog = new JDialog(parentFrame, "Compare Stocks", true);
        dialog.getContentPane().add(view);
        dialog.pack();
        dialog.setVisible(true);
    }

    private static CompareStocksController makeController(ViewModel<CompareStocksState> vm) {
        final CompareStocksPresenter presenter = new CompareStocksPresenter(vm);
        final StockDataInterface dai = new StockDataLoader();
        final CompareStocksInteractor interactor = new CompareStocksInteractor(presenter, dai);
        return new CompareStocksController(interactor);
    }
}
