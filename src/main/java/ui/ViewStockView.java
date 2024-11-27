package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import app.Constants;
import interface_adapters.ViewManagerModel;
import interface_adapters.view_stock.ViewStockController;
import interface_adapters.view_stock.ViewStockViewModel;

/**
 * View for the application with added stock favoriting functionality.
 */
public class ViewStockView {
    private final JPanel mainPanel;
    private final JButton compareButton;
    private final JComboBox<String> stockDropdown;
    private final JButton buyButton;
    private final JButton favoriteButton;
    private final StockDataView stockViewObject;

    private final ViewStockViewModel viewStockViewModel;
    private final ViewStockController viewStockController;

    // Set to store favorited stocks
    private final Set<String> favoritedStocks;

    public ViewStockView(ViewStockViewModel viewStockViewModel,
                         ViewStockController viewStockController) {
        this.viewStockViewModel = viewStockViewModel;
        this.viewStockController = viewStockController;
        this.favoritedStocks = new HashSet<>();

        // Initialize the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Placeholder panel for when no stock is selected
        final JPanel defaultBox = new JPanel(new BorderLayout());
        final JLabel placeholderLabel = new JLabel(Constants.PLACEHOLDER_TEXT, SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Arial", Font.BOLD, Constants.PLACEHOLDER_FONT_SIZE));
        defaultBox.add(placeholderLabel, BorderLayout.CENTER);

        // Initialize StockDataView with sample data
        stockViewObject = new StockDataView();
        stockViewObject.generatePanel();

        // Generate sample data for testing
        final List<Double> samplePrices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            samplePrices.add(100.0 + Math.random() * 20);
        }
        stockViewObject.setSharePrices(samplePrices);

        // CardLayout panel for displaying panels according to stocks selected
        final CardLayout cardLayout = new CardLayout();
        final JPanel stockViews = new JPanel(cardLayout);
        stockViews.add(defaultBox, Constants.NO_STOCKS_SELECTED);
        stockViews.add(stockViewObject.getStockView(), Constants.STOCK_VIEW);

        // Make sure the stock view panel has a preferred size
        stockViewObject.getStockView().setPreferredSize(Constants.STOCK_VIEW_DIMENSION);

        mainPanel.add(stockViews, BorderLayout.CENTER);

        // Manages which panel in stockViews is displayed
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(stockViews, cardLayout, viewManagerModel);

        // Bottom panel to hold buttons and dropdown
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        // Dropdown menu for selecting stocks
        stockDropdown = new JComboBox<>(new String[]{Constants.NO_STOCKS_SELECTED, "Stock A", "Stock B", "Stock C"});
        bottomPanel.add(stockDropdown);

        // Favorite button
        favoriteButton = new JButton("☆ Favorite");
        favoriteButton.setEnabled(false);
        bottomPanel.add(favoriteButton);

        // Response to selecting a stock in dropdown menu
        stockDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String symbol = Objects.requireNonNull(stockDropdown.getSelectedItem()).toString();
                // Check if a stock is selected
                if (!symbol.equals(Constants.NO_STOCKS_SELECTED)) {
                    // Update the stock view with new data, only for when controller is unusable
                    stockViewObject.setSymbol(symbol);
                    stockViewObject.setCompany("Company " + symbol);

                    // Update favorite button state
                    updateFavoriteButtonState(symbol);

                    // Generate new random data for the selected stock, only for when controller is unusable
                    final List<Double> prices = new ArrayList<>();
                    final double basePrice = switch (symbol) {
                        case "Stock A" -> 100.0;
                        case "Stock B" -> 150.0;
                        case "Stock C" -> 200.0;
                        default -> 100.0;
                    };
                    for (int i = 0; i < 10; i++) {
                        prices.add(basePrice + Math.random() * 20);
                    }
                    stockViewObject.setSharePrices(prices);

                    // Show the stock view, only for when controller is unusable
                    cardLayout.show(stockViews, Constants.STOCK_VIEW);
                    viewManagerModel.setState(Constants.STOCK_VIEW);
                    viewManagerModel.firePropertyChanged();

                    // Ensure the panel is revalidated and repainted
                    stockViewObject.getStockView().revalidate();
                    stockViewObject.getStockView().repaint();
                }
                else {
                    // No stock is selected
                    cardLayout.show(stockViews, Constants.NO_STOCKS_SELECTED);
                    favoriteButton.setEnabled(false);
                }
            }
        });

        // Favorite button action listener
        favoriteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String symbol = Objects.requireNonNull(stockDropdown.getSelectedItem()).toString();

                if (!symbol.equals(Constants.NO_STOCKS_SELECTED)) {
                    if (favoritedStocks.contains(symbol)) {
                        // Remove from favorites
                        favoritedStocks.remove(symbol);
                        favoriteButton.setText("☆ Favorite");
                    } else {
                        // Add to favorites
                        favoritedStocks.add(symbol);
                        favoriteButton.setText("★ Favorited");
                    }
                }
            }
        });

        // Button to compare stocks
        compareButton = new JButton("Compare Stocks");
        bottomPanel.add(compareButton);

        // Button to buy stock
        buyButton = new JButton("Buy Stock");
        bottomPanel.add(buyButton);

        // Add bottom panel to the main panel
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the favorite button state based on the current selected stock.
     * @param symbol the stock symbol
     */
    private void updateFavoriteButtonState(String symbol) {
        favoriteButton.setEnabled(true);
        if (favoritedStocks.contains(symbol)) {
            favoriteButton.setText("★ Favorited");
        } else {
            favoriteButton.setText("☆ Favorite");
        }
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

    /**
     * Get the set of favorited stocks.
     * @return Set of favorited stock symbols
     */
    public Set<String> getFavoritedStocks() {
        return new HashSet<>(favoritedStocks);
    }

    /**
     * Action performed.
     * @param evt event
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }
}