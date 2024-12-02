package app;

import java.util.Set;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import interface_adapters.ViewManagerModel;
import interface_adapters.gateways.StockDataLoader;
import interface_adapters.gateways.StockSymbolsLoader;
import interface_adapters.loading_hub.LoadingHubController;
import interface_adapters.loading_hub.LoadingHubUseCaseFactory;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchUseCaseFactory;
import interface_adapters.search.SearchViewModel;
import interface_adapters.text_analyze_stock.StockController;
import interface_adapters.text_analyze_stock.StockControllerFactory;
import interface_adapters.text_analyze_stock.StockViewModel;
import interface_adapters.view_stock.ViewStockController;
import interface_adapters.view_stock.ViewStockUseCaseFactory;
import interface_adapters.view_stock.ViewStockViewModel;
import ui.SearchView;
import ui.ViewStockView;
import ui.compare_stocks.CompareStocksViewDisplayer;
import ui.text_analyze_stock.StockAnalysisView;
import use_cases.StockDataInterface;
import use_cases.SymbolNameDataAccessInterface;
import use_cases.favorites.FavoriteStockInputBoundary;
import use_cases.favorites.FavoriteStockInteractor;
import use_cases.favorites.FavoriteStockOutputBoundary;

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
        final ViewStockViewModel loadingHubViewModel = new ViewStockViewModel();
        final StockViewModel stockViewModel = new StockViewModel();

        // data loaders
        final StockDataInterface stockDataAccessObject = new StockDataLoader();
        final SymbolNameDataAccessInterface symbolDataAccessObject = new StockSymbolsLoader();

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
        final StockDataInterface loadingHubAccessInterface = new StockDataLoader();

        // Create FavoriteStockInteractor with the file storage
        final FavoriteStockInputBoundary favoriteStockInputBoundary =
                new FavoriteStockInteractor(favoriteStockPresenter);

        // Load favorite stocks on startup
        favoriteStockInputBoundary.getFavorites();

        // Create the controllers
        final ViewStockController viewStockController = ViewStockUseCaseFactory.create(
                        viewManagerModel,
                        viewStockViewModel,
                        stockDataAccessObject,
                        symbolDataAccessObject);
        final SearchController searchController = SearchUseCaseFactory.create(
                        viewManagerModel,
                        searchViewModel,
                        symbolDataAccessObject);
        final LoadingHubController loadingHubController = LoadingHubUseCaseFactory.create(
                        viewManagerModel,
                        loadingHubViewModel,
                        loadingHubAccessInterface);
        final StockController predictController = StockControllerFactory.createStockController(
                        stockViewModel,
                        stockDataAccessObject);

        // initializes search view
        final SearchView searchView = new SearchView(searchViewModel, searchController, viewStockController);

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
                    viewManagerModel, viewStockViewModel,
                    viewStockController, searchView, loadingHubController);
            // Initialize ViewStockView and add it to the frame
            viewStockView.setCompareButtonListener(_ -> CompareStocksViewDisplayer.showDialog(frame));

            // Initialize Stock Analysis View
            final StockAnalysisView stockAnalysisView = new StockAnalysisView(stockViewModel, viewStockViewModel,
                    predictController);
            
            frame.add(viewStockView.getMainPanel());

            // Make the frame visible
            frame.setVisible(true);
        });
    }
}
