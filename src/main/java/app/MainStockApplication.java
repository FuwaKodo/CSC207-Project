package main.java.app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.java.Constants;
import main.java.entities.Stock;
import main.java.interface_adapters.ViewManagerModel;
import main.java.interface_adapters.view_stock.ViewStockController;
import main.java.interface_adapters.view_stock.ViewStockViewModel;
import main.java.use_cases.view_stock.ViewStockDataAccessInterface;

/**
 * Main application for stock analysis and comparison.
 *
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

            // Increased window size to better accommodate the graph
            frame.setSize(Constants.MAIN_FRAME_DIMENSION);

            // Set minimum size to prevent the window from becoming too small
            frame.setMinimumSize(Constants.MAIN_FRAME_MIN_DIMENSION);

            // Center the window on the screen
            frame.setLocationRelativeTo(null);

            // Initialize ViewStockView and add it to the frame
            final ViewStockView viewStockView = new ViewStockView(viewStockViewModel, viewStockController);
            frame.add(viewStockView.getMainPanel());

            // Display the GUI
            frame.setVisible(true);
        });
    }
}
