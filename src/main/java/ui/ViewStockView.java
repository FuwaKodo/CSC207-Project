package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import app.Constants;
import interface_adapters.ViewManagerModel;
import interface_adapters.loading_hub.LoadingHubController;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchViewModel;
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
    private SearchView searchView;
    private final CardLayout cardLayout;
    private final JPanel views;

    private final ViewStockViewModel viewStockViewModel;
    private final ViewStockController viewStockController;
    private final SearchController searchController;
    private final LoadingHubController loadingHubController;

    // Set to store favorited stocks
    private final Set<String> favoritedStocks;

    public ViewStockView(ViewStockViewModel viewStockViewModel,
                         ViewStockController viewStockController,
                         SearchController searchController,
                         LoadingHubController loadingHubController) {
        this.viewStockViewModel = viewStockViewModel;
        this.viewStockController = viewStockController;
        this.searchController = searchController;
        this.loadingHubController = loadingHubController;
        this.favoritedStocks = new HashSet<>();

        // Initialize the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Placeholder panel for when no stock is selected
        final JPanel defaultBox = new JPanel(new BorderLayout());
        final JLabel placeholderLabel = new JLabel(Constants.PLACEHOLDER_TEXT, SwingConstants.CENTER);
        placeholderLabel.setFont(Constants.PLACEHOLDER_FONT);
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

        // Date panel to hold buttons and dropdown
        final JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout());

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
                    cardLayout.show(views, Constants.STOCK_VIEW);
                    viewManagerModel.setState(Constants.STOCK_VIEW);
                    viewManagerModel.firePropertyChanged();

                    // Ensure the panel is revalidated and repainted
                    stockViewObject.getStockView().revalidate();
                    stockViewObject.getStockView().repaint();
                }
                else {
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
                    }
                    else {
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

        // Button for startDate and endDate
        final JLabel date1Label = new JLabel("Start Date: ");
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // date1Panel
        JPanel dateSelector1 = new JPanel();
        dateSelector1.setLayout(new GridLayout(1, 3, 5, 5));
        JComboBox<Integer> dayBox1 = new JComboBox<>();
        populateDays(dayBox1, 2020, 1);
        JComboBox<Integer> monthBox1 = new JComboBox<>();
        populateMonthBox(monthBox1, currentYear);
        JComboBox<Integer> yearBox1 = new JComboBox<>();
        for (int i = 2020; i <= currentYear; i++) {
            yearBox1.addItem(i);
        }
        dateSelector1.add(dayBox1);
        dateSelector1.add(monthBox1);
        dateSelector1.add(yearBox1);
        yearBox1.addActionListener(e -> {
            int selectedYear = (int) yearBox1.getSelectedItem();
            populateMonthBox(monthBox1, selectedYear); // Adjust months based on selected year
            updateDays(dayBox1, selectedYear, (int) monthBox1.getSelectedItem());
        });
        monthBox1.addActionListener(e -> {
            int selectedYear = (int) yearBox1.getSelectedItem();
            updateDays(dayBox1, selectedYear, (int) monthBox1.getSelectedItem());
        });
        dateSelector1.putClientProperty("day", dayBox1);
        dateSelector1.putClientProperty("month", monthBox1);
        dateSelector1.putClientProperty("year", yearBox1);
        datePanel.add(date1Label, BorderLayout.WEST);
        datePanel.add(dateSelector1, BorderLayout.WEST);

        // Calendar Input 2
        final JLabel date2Label = new JLabel("  End Date: ");
        // date1Panel
        final JPanel dateSelector2 = new JPanel();
        dateSelector2.setLayout(new GridLayout(1, 3, 5, 5));
        JComboBox<Integer> dayBox2 = new JComboBox<>();
        populateDays(dayBox2, 2020, 1);
        JComboBox<Integer> monthBox2 = new JComboBox<>();
        populateMonthBox(monthBox2, currentYear);
        JComboBox<Integer> yearBox2 = new JComboBox<>();
        for (int i = 2020; i <= currentYear; i++) {
            yearBox2.addItem(i);
        }
        dateSelector2.add(dayBox2);
        dateSelector2.add(monthBox2);
        dateSelector2.add(yearBox2);
        yearBox2.addActionListener(e -> {
            int selectedYear = (int) yearBox2.getSelectedItem();
            populateMonthBox(monthBox2, selectedYear); // Adjust months based on selected year
            updateDays(dayBox2, selectedYear, (int) monthBox2.getSelectedItem());
        });
        monthBox2.addActionListener(e -> {
            int selectedYear = (int) yearBox2.getSelectedItem();
            updateDays(dayBox2, selectedYear, (int) monthBox2.getSelectedItem());
        });
        dateSelector2.putClientProperty("day", dayBox2);
        dateSelector2.putClientProperty("month", monthBox2);
        dateSelector2.putClientProperty("year", yearBox2);
        datePanel.add(date2Label, BorderLayout.WEST);
        datePanel.add(dateSelector2, BorderLayout.WEST);

        final JButton updateButton = new JButton("Update");
        datePanel.add(updateButton, BorderLayout.EAST);

        // Response to clicking updateButton
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Calendar startDateCalendar = Calendar.getInstance();
                startDateCalendar.set((int) yearBox1.getSelectedItem(),
                        (int) monthBox1.getSelectedItem() - 1,
                        (int) dayBox1.getSelectedItem());
                clearTimeFields(startDateCalendar);

                final Calendar endDateCalendar = Calendar.getInstance();
                endDateCalendar.set((int) yearBox2.getSelectedItem(),
                        (int) monthBox2.getSelectedItem() - 1,
                        (int) dayBox2.getSelectedItem());
                clearTimeFields(endDateCalendar);

                final Date startDate = startDateCalendar.getTime();
                final Date endDate = endDateCalendar.getTime();
                // final String stockSymbol = stockDropdown.getSelectedItem().toString();
                // set to stockSymbol = "INTC"
                // TODO: Fix this by changing
                final String stockSymbol = "INTC";

                loadingHubController.execute(stockSymbol, startDate, endDate);
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
        mainPanel.add(views, BorderLayout.CENTER);
        mainPanel.add(datePanel, BorderLayout.NORTH);
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
        // TODO: Fix this!
        // monthBox.removeAllItems();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int lastMonth = Calendar.getInstance().get(Calendar.MONTH) + 1; // Months are 0-based

        // If the selected year is the current year, limit months to the current month
        // Otherwise, populate all 12 months
        int maxMonths = (selectedYear == currentYear) ? lastMonth : 12;
        for (int i = 1; i <= maxMonths; i++) {
            monthBox.addItem(i);
        }
    }

    private void populateDays(JComboBox<Integer> dayBox, int year, int month) {
        dayBox.removeAllItems();
        int daysInMonth = getDaysInMonth(year, month);
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

    /**
     * Updates the favorite button state based on the current selected stock.
     * @param symbol the stock symbol
     */
    private void updateFavoriteButtonState(String symbol) {
        favoriteButton.setEnabled(true);
        if (favoritedStocks.contains(symbol)) {
            favoriteButton.setText(Constants.FAVORITED);
        }
        else {
            favoriteButton.setText(Constants.NOT_FAVORITED);
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
