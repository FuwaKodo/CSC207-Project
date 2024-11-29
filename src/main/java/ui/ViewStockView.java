package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import app.Constants;
import interface_adapters.ViewManagerModel;
import interface_adapters.view_stock.ViewStockController;
import interface_adapters.view_stock.ViewStockViewModel;

/**
 * View for the application that displays stock information and allows users to favorite stocks.
 */
public class ViewStockView {
    /** Main panel that holds all components of the view. */
    private final JPanel mainPanel;

    /** Button to compare selected stocks. */
    private final JButton compareButton;

    /** Dropdown menu for selecting stocks. */
    private final JComboBox<String> stockDropdown;

    /** Button to buy selected stock. */
    private final JButton buyButton;

    /** Button to favorite the selected stock. */
    private final JButton favoriteButton;

    /** Object responsible for displaying stock data. */
    private final StockDataView stockViewObject;

    /** Panel to display favorite stocks. */
    private final JPanel favoritesPanel;

    /** Model for the list of favorite stocks. */
    private final DefaultListModel<String> favoritesListModel;

    /** List component to show favorite stocks. */
    private final JList<String> favoritesList;

    /** ViewModel to manage stock view state. */
    private final ViewStockViewModel viewStockViewModel;

    /** Controller to handle stock view logic. */
    private final ViewStockController viewStockController;

    /** Set to store favorited stocks. */
    private final Set<String> favoritedStocks;

    /**
     * Constructs the ViewStockView with the specified ViewModel and Controller.
     *
     * @param viewStockViewModel the ViewModel managing the stock view state
     * @param viewStockController the Controller handling business logic for the stock view
     */
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

        // Generate sample share prices for testing
        final List<Double> samplePrices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            samplePrices.add(100.0 + Math.random() * 20);
        }
        stockViewObject.setSharePrices(samplePrices);

        // Create favorites panel with adjusted size
        favoritesPanel = new JPanel(new BorderLayout());
        favoritesPanel.setBorder(BorderFactory.createTitledBorder("Favorites"));
        favoritesListModel = new DefaultListModel<>();
        favoritesList = new JList<>(favoritesListModel);
        favoritesList.setPreferredSize(new Dimension(100, (int)(Constants.STOCK_VIEW_DIMENSION.height * 0.7)));
        JScrollPane favoritesScrollPane = new JScrollPane(favoritesList);
        favoritesScrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        favoritesPanel.add(favoritesScrollPane, BorderLayout.CENTER);

        // Create a panel to hold both the stock view and favorites panel
        JPanel stockWithFavorites = new JPanel(new BorderLayout());
        stockWithFavorites.add(stockViewObject.getStockView(), BorderLayout.CENTER);
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        rightPanel.add(favoritesPanel);
        stockWithFavorites.add(rightPanel, BorderLayout.EAST);

        // CardLayout panel for displaying panels based on selected stocks
        final CardLayout cardLayout = new CardLayout();
        final JPanel stockViews = new JPanel(cardLayout);
        stockViews.add(defaultBox, Constants.NO_STOCKS_SELECTED);
        stockViews.add(stockWithFavorites, Constants.STOCK_VIEW);

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
                if (!symbol.equals(Constants.NO_STOCKS_SELECTED)) {
                    stockViewObject.setSymbol(symbol);
                    stockViewObject.setCompany("Company " + symbol);

                    updateFavoriteButtonState(symbol);

                    // Generate share prices based on selected stock
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

                    // Show the stock view
                    cardLayout.show(stockViews, Constants.STOCK_VIEW);
                    viewManagerModel.setState(Constants.STOCK_VIEW);
                    viewManagerModel.firePropertyChanged();

                    stockViewObject.getStockView().revalidate();
                    stockViewObject.getStockView().repaint();
                } else {
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
                    // Toggle favorite status
                    if (favoritedStocks.contains(symbol)) {
                        favoritedStocks.remove(symbol);
                        favoritesListModel.removeElement(symbol);
                        favoriteButton.setText("☆ Favorite");
                    } else {
                        favoritedStocks.add(symbol);
                        favoritesListModel.addElement(symbol);
                        favoriteButton.setText("★ Favorited");
                    }
                    updateFavoritesPanel(); // Update panel size
                }
            }
        });
        updateFavoritesPanel();

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
     * Updates the size of the favorites panel based on the number of favorited stocks.
     */
    private void updateFavoritesPanel() {
        if (favoritedStocks.isEmpty()) {
            favoritesPanel.setPreferredSize(new Dimension(100, 50)); // Small size
        } else {
            favoritesPanel.setPreferredSize(new Dimension(100, (int)(Constants.STOCK_VIEW_DIMENSION.height * 0.7))); // Original size
        }
        favoritesPanel.revalidate();
        favoritesPanel.repaint();
    }

    /**
     * Updates the state of the favorite button based on whether the selected stock is favorited.
     *
     * @param symbol the stock symbol to check
     */
    private void updateFavoriteButtonState(String symbol) {
        favoriteButton.setEnabled(true);
        if (favoritedStocks.contains(symbol)) {
            favoriteButton.setText("★ Favorited");
        } else {
            favoriteButton.setText("☆ Favorite");
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getCompareButton() {
        return compareButton;
    }

    public JComboBox<String> getStockDropdown() {
        return stockDropdown;
    }

    public JButton getBuyButton() {
        return buyButton;
    }

    public Set<String> getFavoritedStocks() {
        return new HashSet<>(favoritedStocks);
    }

    /**
     * Action listener for button clicks.
     *
     * @param evt the action event triggered by button clicks
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }
}