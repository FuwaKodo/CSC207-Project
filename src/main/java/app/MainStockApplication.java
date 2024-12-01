package app;

import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import entities.SharePrices;
import entities.Stock;
import frameworks.FavoriteStockData;
import interface_adapters.ViewManagerModel;
import interface_adapters.gateways.StockDataLoader;
import interface_adapters.loading_hub.LoadingHubController;
import interface_adapters.loading_hub.LoadingHubUseCaseFactory;
import interface_adapters.loading_hub.LoadingHubViewModel;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchUseCaseFactory;
import interface_adapters.search.SearchViewModel;
import interface_adapters.view_stock.ViewStockController;
import interface_adapters.view_stock.ViewStockViewModel;
import ui.ViewStockView;
import ui.compare_stocks.CompareStocksViewDisplayer;
import use_cases.loading_hub.LoadingHubAccessInterface;
import use_cases.loading_hub.LoadingHubInteractor;
import use_cases.search.SearchDataAccessInterface;
import use_cases.view_stock.ViewStockDataAccessInterface;
import use_cases.view_stock.ViewStockUseCaseFactory;

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

        // Create the ViewStockViewModel and SearchViewModel
        final ViewStockViewModel viewStockViewModel = new ViewStockViewModel();
        final SearchViewModel searchViewModel = new SearchViewModel();
        final LoadingHubViewModel loadingHubViewModel = new LoadingHubViewModel();

        // data loader for search use case
        final SearchDataAccessInterface searchDataAccessObject = new StockSymbolsLoader();
        // Create a temporary implementation of the ViewStockDataAccessInterface
        final ViewStockDataAccessInterface viewStockDataAccessInterface = new ViewStockDataAccessInterface() {
            @Override
            public Stock getStock(String symbol) {
                return null; // Placeholder implementation
            }
        };


        // Create FavoriteStockOutputBoundary implementation (presenter)
        final FavoriteStockOutputBoundary favoriteStockPresenter = new FavoriteStockOutputBoundary() {
            @Override
            public void presentFavoriteToggled(String symbol, boolean isFavorited) {
                // Implement presentation logic for favorite toggle
            }

            @Override
            public void presentFavorites(Set<String> favorites) {
                // Implement presentation logic for favorites list
            }
        };
        final StockDataInterface loadingHubAccessInterface = StockDataLoader;

        // Create FavoriteStockInteractor with the file storage
        final FavoriteStockInputBoundary favoriteStockInputBoundary =
                new FavoriteStockInteractor(favoriteStockPresenter);

        // Load favorite stocks on startup
        favoriteStockInputBoundary.getFavorites();

        // Create the ViewStockController using the factory
        final ViewStockController viewStockController =
                ViewStockUseCaseFactory.create(
                        viewManagerModel,
                        viewStockViewModel,
                        viewStockDataAccessInterface,
                        favoriteStockInputBoundary
                );

        // Create the SearchController
        final SearchController searchController =
                SearchUseCaseFactory.create(viewManagerModel, searchViewModel, searchDataAccessInterface);
        final LoadingHubController loadingHubController =
                LoadingHubUseCaseFactory.create(viewManagerModel, loadingHubViewModel, loadingHubAccessInterface);

        // Use SwingUtilities to ensure the GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame("Stock Analysis Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(Constants.MAIN_FRAME_DIMENSION);
            frame.setMinimumSize(Constants.MAIN_FRAME_MIN_DIMENSION);
            // Center the window
            frame.setLocationRelativeTo(null);

            // Create the view and add it to the frame
            final ViewStockView viewStockView = new ViewStockView(
                    viewStockViewModel, viewStockController, searchController, loadingHubController);
            // Initialize ViewStockView and add it to the frame
            viewStockView.setCompareButtonListener(_ -> CompareStocksViewDisplayer.showDialog(frame));
            
            // Initialize search view
            viewStockView.setSearchView(searchViewModel);
            
            frame.add(viewStockView.getMainPanel());

            // Make the frame visible
            frame.setVisible(true);
        });
    }
}
