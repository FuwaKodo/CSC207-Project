package ui.compare_stocks;

import interface_adapters.ViewModel;
import interface_adapters.compare_stocks.CompareStocksState;
import ui.compare_stocks.CompareStocksView;

import javax.swing.*;

public class CompareStocksViewDisplayer {
    private CompareStocksViewDisplayer() {}

    public static void showDialog(JFrame parentFrame) {
        final JDialog dialog = new JDialog(parentFrame, "Compare Stocks", true);
        final ViewModel<CompareStocksState> vm = new ViewModel<>("Compare Stocks");
        final CompareStocksView view = new CompareStocksView(vm);
        dialog.getContentPane().add(view.getMainPanel());
        dialog.pack();
        dialog.setVisible(true);
    }
}
