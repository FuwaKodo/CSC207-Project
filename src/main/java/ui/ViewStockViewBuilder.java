package ui;

import interface_adapters.view_stock.ViewStockController;
import interface_adapters.view_stock.ViewStockViewModel;

import javax.swing.*;
import java.awt.*;

public class ViewStockViewBuilder {
    private JPanel mainPanel;

    public ViewStockViewBuilder(ViewStockViewModel viewStockViewModel,
                                ViewStockController viewStockController) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
    }

    public JButton makeCompareButton() {
        JButton button = new JButton("Compare Stocks");
        return button;
    }
}
