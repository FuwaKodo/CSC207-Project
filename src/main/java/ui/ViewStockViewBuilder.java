package ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import interface_adapters.view_stock.ViewStockController;
import interface_adapters.view_stock.ViewStockViewModel;

/**
 * Builder class for ViewStockView.
 */
public class ViewStockViewBuilder {
    private JPanel mainPanel;

    public ViewStockViewBuilder(ViewStockViewModel viewStockViewModel,
                                ViewStockController viewStockController) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
    }

    /**
     * Returns the button for comparing stocks.
     * @return a compare button
     */
    public JButton makeCompareButton() {
        final JButton button = new JButton("Compare Stocks");
        return button;
    }
}
