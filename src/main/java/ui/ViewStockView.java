package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.*;

import app.Constants;
import entities.SharePrices;
import interface_adapters.ViewManagerModel;
import interface_adapters.favoritesIA.FavoritesController;
import interface_adapters.gateways.StockSymbolsLoader;
import interface_adapters.loading_hub.LoadingHubController;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchViewModel;
import interface_adapters.view_stock.ViewStockController;
import interface_adapters.view_stock.ViewStockViewModel;
import use_cases.SymbolNameDataAccessInterface;

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
    private final LoadingHubController loadingHubController;

    /** Manager for favorites functionality. */
    private final FavoritesController favoritesController;

    /** Panel to hold stock and favorites. */
    private final JPanel stockWithFavorites;

    /**
     * Constructs the ViewStockView with the specified ViewModel and Controller.
     *
     * @param viewManagerModel the view manager model responsible for switching view
     * @param viewStockViewModel the ViewModel managing the stock view state
     * @param viewStockController the Controller handling business logic for the stock view
     * @param searchController the controller for search use case
     * @param loadingHubController the controller for loading hub use case
     */
    public ViewStockView(ViewManagerModel viewManagerModel,
                         ViewStockViewModel viewStockViewModel,
                         ViewStockController viewStockController,
                         SearchController searchController,
                         LoadingHubController loadingHubController) {
        this.viewStockViewModel = viewStockViewModel;
        this.viewStockController = viewStockController;
        this.searchController = searchController;
        this.loadingHubController = loadingHubController;
        this.favoritesController = new FavoritesController();
        final SymbolNameDataAccessInterface symbolDataAccessObject = new StockSymbolsLoader();

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
        new ViewManager(views, cardLayout, viewManagerModel);

        // Create a panel to hold stock view and favorites
        stockWithFavorites = new JPanel(new BorderLayout());
        stockWithFavorites.add(stockViewObject.getMainPanel(), BorderLayout.CENTER);

        // Initially, do not add favorites panel to avoid showing on title screen
        final JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Make sure the stock view panel has a preferred size
        stockViewObject.getMainPanel().setPreferredSize(Constants.STOCK_VIEW_DIMENSION);

        // Bottom panel to hold buttons and dropdown
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        // Dropdown menu for selecting stocks
        final List<String> stocksList = new ArrayList<>();
        stocksList.add(Constants.NO_STOCKS_SELECTED);
        stocksList.addAll(symbolDataAccessObject.getSymbols());
        final String[] stocksArray = stocksList.toArray(new String[0]);
        stockDropdown = new JComboBox<>(stocksArray);
        bottomPanel.add(stockDropdown);

        // Favorite button is added to the bottom panel
        bottomPanel.add(favoritesController.getFavoriteButton());

        // Date panel to hold buttons and dropdown
        final JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout());

        // Response to selecting a stock in dropdown menu
        stockDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String symbol = Objects.requireNonNull(stockDropdown.getSelectedItem()).toString();
                if (!symbol.equals(Constants.NO_STOCKS_SELECTED)) {
                    // Execute the view stock use case
                    viewStockController.execute(symbol);

                    // Set symbol and company from the state
                    stockViewObject.setSymbol(viewStockViewModel.getState().getSymbol());
                    stockViewObject.setCompany(viewStockViewModel.getState().getCompany());

                    // Update favorite button state
                    favoritesController.updateFavoriteButtonState(symbol);

                    // Use share prices from the state
                    final SharePrices sharePrices = viewStockViewModel.getState().getSharePrices();
                    if (sharePrices != null) {
                        // Use close prices for the stock view
                        stockViewObject.setSharePrices(sharePrices.getClosePrices());
                    }

                    // Add favorites panel to the right side when a stock is selected
                    rightPanel.removeAll();
                    rightPanel.add(favoritesController.getFavoritesPanel());
                    stockWithFavorites.add(rightPanel, BorderLayout.EAST);

                    // Show the stock view
                    cardLayout.show(views, Constants.STOCK_VIEW);
                    viewManagerModel.setState(Constants.STOCK_VIEW);
                    viewManagerModel.firePropertyChanged();

                    stockViewObject.getMainPanel().revalidate();
                    stockViewObject.getMainPanel().repaint();
                }
                else {
                    // No stock is selected
                    stockWithFavorites.remove(rightPanel);
                    cardLayout.show(views, Constants.NO_STOCKS_SELECTED);
                    favoritesController.updateFavoriteButtonState(symbol);
                }
            }
        });

        // Favorite button action listener
        favoritesController.getFavoriteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String symbol = Objects.requireNonNull(stockDropdown.getSelectedItem()).toString();
                favoritesController.toggleFavorite(symbol);
            }
        });

        // Input box and button for searching stocks
        final JTextField searchField = new JTextField(8);
        bottomPanel.add(searchField);
        final JButton searchButton = new JButton("Search");
        bottomPanel.add(searchButton);

        // Response to clicking searchButton
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // executes search use case with input from searchField
                searchController.execute(searchField.getText());
            }
        });

        // Button for startDate and endDate
        final JLabel date1Label = new JLabel("Start Date: ");
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // date1Panel
        final JPanel dateSelector1 = new JPanel();
        dateSelector1.setLayout(new GridLayout(Constants.DATE_SELECTOR_GRID_ROWS,
                Constants.DATE_SELECTOR_GRID_COLS,
                Constants.DATE_SELECTOR_GRID_HGAP,
                Constants.DATE_SELECTOR_GRID_VGAP));
        final JComboBox<Integer> dayBox1 = new JComboBox<>();
        populateDays(dayBox1, Constants.LATEST_YEAR, 1);
        final JComboBox<Integer> monthBox1 = new JComboBox<>();
        populateMonthBox(monthBox1, currentYear);
        final JComboBox<Integer> yearBox1 = new JComboBox<>();
        for (int i = Constants.LATEST_YEAR; i <= currentYear; i++) {
            yearBox1.addItem(i);
        }
        dateSelector1.add(dayBox1);
        dateSelector1.add(monthBox1);
        dateSelector1.add(yearBox1);

        yearBox1.addActionListener(actionEvent -> {
            yearBoxEvent(yearBox1, monthBox1);
        });

        monthBox1.addActionListener(actionEvent -> {
            final int selectedYear = (int) yearBox1.getSelectedItem();
            final Object selectedMonth = monthBox1.getSelectedItem();

            if (selectedMonth != null) {
                updateDays(dayBox1, selectedYear, (int) selectedMonth);
            }
        });

        // dateSelector1 property setup
        dateSelector1.putClientProperty("day", dayBox1);
        dateSelector1.putClientProperty("month", monthBox1);
        dateSelector1.putClientProperty("year", yearBox1);

        datePanel.add(date1Label, BorderLayout.WEST);
        datePanel.add(dateSelector1, BorderLayout.WEST);

        // Calendar Input 2
        final JLabel date2Label = new JLabel("  End Date: ");
        // date1Panel
        final JPanel dateSelector2 = new JPanel();
        dateSelector2.setLayout(new GridLayout(Constants.DATE_SELECTOR_GRID_ROWS,
                Constants.DATE_SELECTOR_GRID_COLS,
                Constants.DATE_SELECTOR_GRID_HGAP,
                Constants.DATE_SELECTOR_GRID_VGAP));
        final JComboBox<Integer> dayBox2 = new JComboBox<>();
        populateDays(dayBox2, Constants.LATEST_YEAR, 1);
        final JComboBox<Integer> monthBox2 = new JComboBox<>();
        populateMonthBox(monthBox2, currentYear);
        final JComboBox<Integer> yearBox2 = new JComboBox<>();
        for (int i = Constants.LATEST_YEAR; i <= currentYear; i++) {
            yearBox2.addItem(i);
        }

        dateSelector2.add(dayBox2);
        dateSelector2.add(monthBox2);
        dateSelector2.add(yearBox2);

        yearBox2.addActionListener(actionEvent -> {
            yearBoxEvent(yearBox2, monthBox2);
        });

        monthBox2.addActionListener(actionEvent -> {
            final int selectedYear = (int) yearBox2.getSelectedItem();
            final Object selectedMonth = monthBox2.getSelectedItem();
            if (selectedMonth != null) {
                updateDays(dayBox2, selectedYear, (int) selectedMonth);
            }
        });

        // dateSelector2 property setup
        dateSelector2.putClientProperty("day", dayBox2);
        dateSelector2.putClientProperty("month", monthBox2);
        dateSelector2.putClientProperty("year", yearBox2);

        datePanel.add(date2Label, BorderLayout.WEST);
        datePanel.add(dateSelector2, BorderLayout.WEST);

        final JButton updateButton = new JButton("Update");
        datePanel.add(updateButton, BorderLayout.EAST);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Safely get values from dateSelector1 (startDate)
                JComboBox<Integer> dayBox1 = (JComboBox<Integer>) dateSelector1.getClientProperty("day");
                JComboBox<Integer> monthBox1 = (JComboBox<Integer>) dateSelector1.getClientProperty("month");
                JComboBox<Integer> yearBox1 = (JComboBox<Integer>) dateSelector1.getClientProperty("year");

                // Safely get values from dateSelector2 (endDate)
                JComboBox<Integer> dayBox2 = (JComboBox<Integer>) dateSelector2.getClientProperty("day");
                JComboBox<Integer> monthBox2 = (JComboBox<Integer>) dateSelector2.getClientProperty("month");
                JComboBox<Integer> yearBox2 = (JComboBox<Integer>) dateSelector2.getClientProperty("year");

                // Check for null to avoid null pointer exceptions
                if (dayBox1.getSelectedItem() == null || monthBox1.getSelectedItem() == null || yearBox1.getSelectedItem() == null ||
                        dayBox2.getSelectedItem() == null || monthBox2.getSelectedItem() == null || yearBox2.getSelectedItem() == null) {
                    // Display error message or exit early
                    JOptionPane.showMessageDialog(mainPanel, "Please select valid dates.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create Calendar instances for start and end dates
                Calendar startDateCalendar = Calendar.getInstance();
                startDateCalendar.set((int) yearBox1.getSelectedItem(),
                        (int) monthBox1.getSelectedItem() - 1,
                        (int) dayBox1.getSelectedItem());

                Calendar endDateCalendar = Calendar.getInstance();
                endDateCalendar.set((int) yearBox2.getSelectedItem(),
                        (int) monthBox2.getSelectedItem() - 1,
                        (int) dayBox2.getSelectedItem());

                // Validate that start date is not after end date
                if (startDateCalendar.after(endDateCalendar)) {
                    JOptionPane.showMessageDialog(mainPanel,
                            "Start date cannot be after End date. Please adjust the dates.",
                            "Date Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Proceed with logic if dates are valid
                Date startDate = startDateCalendar.getTime();
                Date endDate = endDateCalendar.getTime();

                // Assuming stockSymbol is predefined or fetched from another component
                // Replace with actual logic to get stock symbol
                String stockSymbol = "INTC";

                // Execute business logic
                loadingHubController.execute(stockSymbol, startDate, endDate);
            }
        });


        // Button to compare stocks
        compareButton = new JButton("Compare Stocks");
        bottomPanel.add(compareButton);

        // Button to buy stock
        buyButton = new JButton("Buy Stock");
        bottomPanel.add(buyButton);

        // adding views to views
        views.add(defaultBox, Constants.NO_STOCKS_SELECTED);
        views.add(stockWithFavorites, Constants.STOCK_VIEW);

        // adding panels to mainPanel
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(views, BorderLayout.CENTER);
        mainPanel.add(datePanel, BorderLayout.NORTH);
    }

    private void yearBoxEvent(JComboBox<Integer> yearBox1, JComboBox<Integer> monthBox1) {
        final int selectedYear = (int) yearBox1.getSelectedItem();
        final ActionListener[] monthListeners = monthBox1.getActionListeners();
        for (ActionListener listener : monthListeners) {
            monthBox1.removeActionListener(listener);
        }
        populateMonthBox(monthBox1, selectedYear);
        for (ActionListener listener : monthListeners) {
            monthBox1.addActionListener(listener);
        }
    }

    /**
     * Clears the time fields of the given Calendar object, setting the time to midnight (00:00:00.000).
     * @param calendar the Calendar object to modify. Must not be null.
     */
    public void clearTimeFields(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void populateMonthBox(JComboBox<Integer> monthBox, int selectedYear) {
        monthBox.removeAllItems();
        final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        // Months are 0-based
        final int lastMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        // If the selected year is the current year, limit months to the current month
        // Otherwise, populate all 12 months
        int maxMonths = 12;
        if (selectedYear == currentYear) {
            maxMonths = lastMonth;
        }

        for (int i = 1; i <= maxMonths; i++) {
            monthBox.addItem(i);
        }
        monthBox.setSelectedItem(1);
    }

    private void populateDays(JComboBox<Integer> dayBox, int year, int month) {
        dayBox.removeAllItems();
        final int daysInMonth = getDaysInMonth(year, month);
        Calendar today = Calendar.getInstance();

        for (int i = 1; i <= daysInMonth; i++) {
            // Restrict days to today if the year and month are the current ones
            if (year == today.get(Calendar.YEAR) && month == (today.get(Calendar.MONTH) + 1) && i > today.get(Calendar.DAY_OF_MONTH)) {
                break;
            }
            dayBox.addItem(i);
        }
    }

    private void updateDays(JComboBox<Integer> dayBox, int year, int month) {
        populateDays(dayBox, year, month);
    }

    private int getDaysInMonth(int year, int month) {
        if (month == 2) { // February
            return isLeapYear(year) ? 29 : 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) { // April, June, September, November
            return 30;
        } else {
            return 31; // Other months
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
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
        return favoritesController.getFavoritedStocks();
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
