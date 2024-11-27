package ui.compare_stocks;

import entities.Stock;
import interface_adapters.ViewModel;
import interface_adapters.compare_stocks.CompareStocksController;
import interface_adapters.compare_stocks.CompareStocksPresenter;
import interface_adapters.compare_stocks.CompareStocksState;
import use_cases.StockDataAccessInterface;
import use_cases.compare_stocks.CompareStocksInteractor;

import javax.swing.*;
import java.util.List;

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
        final StockDataAccessInterface dai = new StockDataAccessInterface() {
            @Override
            public List<String> getAllCompanyNames() {
                return List.of("Stock A", "Stock B", "Stock C");
            }

            @Override
            public Stock getStockByCompany(String name) {
                return null;
            }
        };
        final CompareStocksInteractor interactor = new CompareStocksInteractor(presenter, dai);
        return new CompareStocksController(interactor);
    }
}
