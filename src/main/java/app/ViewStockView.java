package main.java.app;

import main.java.Constants;
import main.java.interface_adapters.ViewManagerModel;
import main.java.interface_adapters.view_stock.ViewStockController;
import main.java.interface_adapters.view_stock.ViewStockState;
import main.java.interface_adapters.view_stock.ViewStockViewModel;
import main.java.view.StockDataView;
import main.java.view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * View for the application
 */
public class ViewStockView {
    private JPanel mainPanel;
    private JButton compareButton;
    private JComboBox<String> stockDropdown;
    private JButton buyButton;

    private ViewStockViewModel viewStockViewModel;
    private ViewStockController viewStockController;

    public ViewStockView(ViewStockViewModel viewStockViewModel,
                         ViewStockController viewStockController) {
        this.viewStockViewModel = viewStockViewModel;
        this.viewStockController = viewStockController;

        // Initialize the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Placeholder panel for when no stock is selected
        final JPanel defaultBox = new JPanel(new BorderLayout());
        final JLabel placeholderLabel = new JLabel(Constants.PLACE_HOLDER, SwingConstants.CENTER);
        placeholderLabel.setFont(new Font("Arial", Font.BOLD, 24));
        defaultBox.add(placeholderLabel, BorderLayout.CENTER);

        // Panel for displaying stocks data
        final StockDataView stockViewObject = new StockDataView();
        stockViewObject.generatePanel();

        // CardLayout panel for displaying panels according to stocks selected
        final CardLayout cardLayout = new CardLayout();
        final JPanel stockViews = new JPanel(cardLayout);
        stockViews.add(defaultBox, Constants.SELECT_STOCK);
        stockViews.add(stockViewObject.getStockView(), Constants.VIEW_STOCK);
        // final List<String> isInStockViews = new ArrayList<>(List.of(Constants.SELECT_STOCK));
        mainPanel.add(stockViews, BorderLayout.CENTER);

        // Manages which stock is displayed
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(stockViews, cardLayout, viewManagerModel);

        // Bottom panel to hold buttons and dropdown
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        // Dropdown menu for selecting stocks
        stockDropdown = new JComboBox<>(new String[]{Constants.SELECT_STOCK, "Stock A", "Stock B", "Stock C"});
        bottomPanel.add(stockDropdown);

        // Response to selecting a stock in dropdown menu
        stockDropdown.addActionListener(new ActionListener() {
            /**
             * Invoked when a stock is selected.
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                final String symbol = Objects.requireNonNull(stockDropdown.getSelectedItem()).toString();
                // Check if selected option's panel is already generated
                if (symbol.equals(Constants.SELECT_STOCK)) {
                    cardLayout.show(stockViews, symbol);
                }
                else {
                    // viewStockController.execute(symbol);
                    stockViewObject.setSymbol(viewStockViewModel.getState().getSymbol());
                    stockViewObject.setCompany(viewStockViewModel.getState().getCompany());
                    stockViewObject.setSharePrices(viewStockViewModel.getState().getSharePrices());

                    // Only for testing rn!
                    stockViewObject.setSymbol(symbol);
                    stockViewObject.setCompany("-Company name here-");

                    viewManagerModel.setState(Constants.VIEW_STOCK);
                    viewManagerModel.firePropertyChanged();
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
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        final LoginState state = (LoginState) evt.getNewValue();
//        setFields(state);
//        usernameErrorField.setText(state.getLoginError());
//    }
}

