package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import app.Constants;
import interface_adapters.ViewManagerModel;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchViewModel;
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

    /** Button to favorite selected stock. */
    private final JButton favoriteButton;

    /** Object responsible for displaying stock data. */
    private final StockDataView stockViewObject;
    private SearchView searchView;
    private final CardLayout cardLayout;
    private final JPanel views;

    /** ViewModel to manage stock view state. */
    private final ViewStockViewModel viewStockViewModel;

    /** Controller to handle stock view logic. */
    private final ViewStockController viewStockController;
    private final SearchController searchController;

    /** Set to store favorited stocks */
    private final Set<String> favoritedStocks;

    /**
     * Constructs the ViewStockView with the specified ViewModel and Controller.
     *
     * @param viewStockViewModel the ViewModel managing the stock view state
     * @param viewStockController the Controller handling business logic for the stock view
     */
    public ViewStockView(ViewStockViewModel viewStockViewModel,
                         ViewStockController viewStockController,
                         SearchController searchController) {
        this.viewStockViewModel = viewStockViewModel;
        this.viewStockController = viewStockController;
        this.searchController = searchController;
        this.favoritedStocks = new HashSet<>();

        // Initialize the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Placeholder panel for when no stock is selected
        final JPanel defaultBox = new JPanel(new BorderLayout());
        defaultBox.setPreferredSize(new Dimension(Constants.STOCK_VIEW_DIMENSION.width, Constants.STOCK_VIEW_DIMENSION.height));
        final JLabel placeholderLabel = new JLabel(Constants.PLACEHOLDER_TEXT, SwingConstants.CENTER);
        placeholderLabel.setFont(Constants.PLACEHOLDER_FONT);
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

        // Initializes views
        cardLayout = new CardLayout();
        views = new JPanel(cardLayout);

        // Manages which panel in views is displayed
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // Make sure the stock view panel has a preferred size
        stockViewObject.getStockView().setPreferredSize(Constants.STOCK_VIEW_DIMENSION);

        // Bottom panel to hold buttons and dropdown
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        // Dropdown menu for selecting stocks
        stockDropdown = new JComboBox<>(new String[]{Constants.NO_STOCKS_SELECTED, "Stock A", "Stock B", "Stock C"});
        bottomPanel.add(stockDropdown);

        // Favorite button
        favoriteButton = new JButton(Constants.NOT_FAVORITED);
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

                    // Update favorite button state
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
                    cardLayout.show(views, Constants.STOCK_VIEW);
                    viewManagerModel.setState(Constants.STOCK_VIEW);
                    viewManagerModel.firePropertyChanged();

                    stockViewObject.getStockView().revalidate();
                    stockViewObject.getStockView().repaint();
                } else {
                    // No stock is selected
                    cardLayout.show(views, Constants.NO_STOCKS_SELECTED);
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
                        favoriteButton.setText(Constants.NOT_FAVORITED);
                    } else {
                        // Add to favorites
                        favoritedStocks.add(symbol);
                        favoriteButton.setText(Constants.FAVORITED);
                    }
                }
            }
        });

        // adding views to views and all of them to mainPanel
        views.add(defaultBox, Constants.NO_STOCKS_SELECTED);
        views.add(stockViewObject.getStockView(), Constants.STOCK_VIEW);
        mainPanel.add(views, BorderLayout.CENTER);

        // Input box and button for searching stocks
        final JTextField searchField = new JTextField(8);
        bottomPanel.add(searchField);
        final JButton searchButton = new JButton("Search");
        bottomPanel.add(searchButton);

        // Response to clicking searchButton
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String input = searchField.getText();
                searchController.execute(input);
                searchView.updateSearchResult();

                cardLayout.show(views, Constants.SEARCH_VIEW);
                viewManagerModel.setState(Constants.SEARCH_VIEW);
                viewManagerModel.firePropertyChanged();
                searchView.getMainPanel().revalidate();
                searchView.getMainPanel().repaint();
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
            favoriteButton.setText(Constants.FAVORITED);
        } else {
            favoriteButton.setText(Constants.NOT_FAVORITED);
        }
    }

    // Getter for the main panel
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

    /**
     * Initializes searchView and add it to mainPanel.
     * @param searchViewModel SearchViewModel object
     */
    public void setSearchView(SearchViewModel searchViewModel) {
        this.searchView = new SearchView(searchViewModel, viewStockController);
        views.add(searchView.getMainPanel(), searchView.getViewName());
    }

    /**
     * Get the set of favorited stocks.
     * @return Set of favorited stock symbols
     */
    public Set<String> getFavoritedStocks() {
        return new HashSet<>(favoritedStocks);
    }

    public void setCompareButtonListener(ActionListener actionListener) {
        compareButton.addActionListener(actionListener);
    }

    /**
     * Action performed.
     * @param evt event
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }
}