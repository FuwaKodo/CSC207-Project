package app;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import entities.Stock;
import interface_adapters.ViewManagerModel;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchViewModel;
import interface_adapters.view_stock.ViewStockController;
import interface_adapters.view_stock.ViewStockViewModel;
import ui.compare_stocks.CompareStocksViewDisplayer;
import ui.ViewStockView;
import use_cases.search.SearchDataAccessInterface;
import interface_adapters.search.SearchUseCaseFactory;
import use_cases.view_stock.ViewStockDataAccessInterface;
import use_cases.view_stock.ViewStockUseCaseFactory;

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
        final ViewStockViewModel viewStockViewModel = new ViewStockViewModel();
        final SearchViewModel searchViewModel = new SearchViewModel();

        // sample interfaces, will be implemented later
        // TODO replace with data access object when possible
        final ViewStockDataAccessInterface viewStockDataAccessInterface = new ViewStockDataAccessInterface() {
            @Override
            public Stock getStock(String symbol) {
                return null;
            }
        };
        final SearchDataAccessInterface searchDataAccessInterface = new SearchDataAccessInterface() {
            @Override
            public List<String> getSymbols() {
                return List.of("AAPL", "NVDA", "MFC", "L", "INTC");
            }
        };

        final ViewStockController viewStockController =
                ViewStockUseCaseFactory.create(viewManagerModel, viewStockViewModel, viewStockDataAccessInterface);
        final SearchController searchController =
                SearchUseCaseFactory.create(viewManagerModel, searchViewModel, searchDataAccessInterface);

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

            // Initialize ViewStockView and add it to views
            final ViewStockView viewStockView = new ViewStockView(
                    viewStockViewModel, viewStockController, searchController);
            // Initialize ViewStockView and add it to the frame
            viewStockView.setCompareButtonListener(_ -> CompareStocksViewDisplayer.showDialog(frame));
            frame.add(viewStockView.getMainPanel());

            // Initialize searchView
            viewStockView.setSearchView(searchViewModel);

            // Display the GUI
            frame.setVisible(true);
        });
    }
}
