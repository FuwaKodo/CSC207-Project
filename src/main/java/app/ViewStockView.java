package main.java.app;

import javax.swing.*;
import java.awt.*;

public class ViewStockView {
    private JPanel mainPanel;
    private JButton compareButton;
    private JComboBox<String> stockDropdown;
    private JButton buyButton;

    public ViewStockView() {
        // Initialize the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Placeholder label for viewing stock data
        JLabel placeholderLabel = new JLabel("View Stock Data", SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(placeholderLabel, BorderLayout.CENTER);

        // Bottom panel to hold buttons and dropdown
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        // Dropdown menu for selecting stocks
        stockDropdown = new JComboBox<>(new String[]{"Select Stock", "Stock A", "Stock B", "Stock C"});
        bottomPanel.add(stockDropdown);

        // Button to compare stocks
        compareButton = new JButton("Compare Stocks");
        bottomPanel.add(compareButton);

        // Button to buy stock
        buyButton = new JButton("Buy Stock");
        bottomPanel.add(buyButton);

        // Add bottom panel to the main panel
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    // Getter for the main panel
    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Additional getters if needed for event handling
    public JButton getCompareButton() {
        return compareButton;
    }

    public JComboBox<String> getStockDropdown() {
        return stockDropdown;
    }

    public JButton getBuyButton() {
        return buyButton;
    }
}

