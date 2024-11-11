package main.java.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * The view the user sees when checking the information of a stock (view_stock use case).
 */
public class StockDataView {
    private String symbol;
    private String company;
    private List<Double> sharePrices;
    private JPanel stockView;
    private JLabel symbolLabel;
    private JLabel companyLabel;

    public StockDataView() {
        this.symbol = "";
        this.symbolLabel = new JLabel(String.format("Symbol: %s", symbol));
        this.company = "";
        this.companyLabel = new JLabel(String.format("Company: %s", company));
        this.sharePrices = null;
        this.stockView = new JPanel();
    }

    public void generatePanel() {
        stockView.setLayout(new BoxLayout(stockView, BoxLayout.Y_AXIS));

        // Display company name
        stockView.add(companyLabel);

        // Display stock symbol
        stockView.add(symbolLabel);
    }

    public JPanel getStockView() {
        return stockView;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
        symbolLabel.setText(String.format("Symbol: %s", symbol));
    }

    public void setCompany(String company) {
        this.company = company;
        companyLabel.setText(String.format("Symbol: %s", company));
    }

    public void setSharePrices(List<Double> sharePrices) {
        this.sharePrices = sharePrices;
    }
}
