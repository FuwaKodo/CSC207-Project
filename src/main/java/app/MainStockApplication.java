package main.java.app;


import main.java.entities.Stock;
import main.java.interface_adapters.ViewManagerModel;
import main.java.interface_adapters.view_stock.ViewStockController;
import main.java.interface_adapters.view_stock.ViewStockViewModel;
import main.java.use_cases.view_stock.ViewStockDataAccessInterface;

import javax.swing.*;
import java.awt.*;


/**
 * Main application for stock analysis and comparison.
 * <p>
 * This application provides functionality to:
 * - View individual stock data and metrics
 * - Compare different stocks using various comparison methods
 * - Analyze stock metrics and share prices
 * </p>
 */
public class MainStockApplication {

    /**
     * Javadoc to prevent CheckStyle error.
     * @param args args
     */
    public static void main(String[] args) {

        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        final ViewStockDataAccessInterface viewStockDataAccessInterface = new ViewStockDataAccessInterface() {
            @Override
            public Stock getStock(String symbol) {
                return null;
            }
        };
        final ViewStockController viewStockController =
                ViewStockUseCaseFactory.create(viewManagerModel, viewStockDataAccessInterface);
        final ViewStockViewModel viewStockViewModel = new ViewStockViewModel();

        // Set up the main application frame
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame("Stock Analysis Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            // Initialize ViewStockView and add it to the frame
            final ViewStockView viewStockView = new ViewStockView(viewStockViewModel, viewStockController);
            frame.add(viewStockView.getMainPanel());

            // Display the GUI
            frame.setVisible(true);
        });
    }
}
