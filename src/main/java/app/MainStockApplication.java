package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import entities.Stock;
import interface_adapters.ViewManagerModel;
import interface_adapters.view_stock.ViewStockController;
import interface_adapters.view_stock.ViewStockViewModel;
import ui.ViewStockView;
import use_cases.favorites.FavoriteStockInputData;
import use_cases.view_stock.ViewStockDataAccessInterface;
import use_cases.view_stock.ViewStockUseCaseFactory;
import use_cases.favorites.FavoriteStockInputBoundary;

/**
 * Main class for launching the Stock Analysis Application.
 */
public class MainStockApplication {

    /**
     * The entry point of the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create the ViewManagerModel to manage the view state
        final ViewManagerModel viewManagerModel = new ViewManagerModel();

        // Create a temporary implementation of the ViewStockDataAccessInterface
        final ViewStockDataAccessInterface viewStockDataAccessInterface = new ViewStockDataAccessInterface() {
            @Override
            public Stock getStock(String symbol) {
                return null; // Placeholder implementation
            }
        };

        // Create a temporary implementation of FavoriteStockInputBoundary
        final FavoriteStockInputBoundary favoriteStockInputBoundary = new FavoriteStockInputBoundary() {
            @Override
            public void toggleFavorite(FavoriteStockInputData inputData) {
                // Temporary behavior: implement or leave empty
            }

            @Override
            public void getFavorites() {
                // Temporary behavior: implement or leave empty
            }
        };

        // Create the ViewStockController using the factory
        final ViewStockController viewStockController =
                ViewStockUseCaseFactory.create(
                        viewManagerModel,
                        viewStockDataAccessInterface,
                        favoriteStockInputBoundary
                );

        // Initialize the ViewStockViewModel
        final ViewStockViewModel viewStockViewModel = new ViewStockViewModel();

        // Use SwingUtilities to ensure the GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame("Stock Analysis Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(Constants.MAIN_FRAME_DIMENSION);
            frame.setMinimumSize(Constants.MAIN_FRAME_MIN_DIMENSION);
            frame.setLocationRelativeTo(null); // Center the window

            // Create the view and add it to the frame
            final ViewStockView viewStockView = new ViewStockView(viewStockViewModel, viewStockController);
            frame.add(viewStockView.getMainPanel());

            // Make the frame visible
            frame.setVisible(true);
        });
    }
}