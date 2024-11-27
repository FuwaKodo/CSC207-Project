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

public class MainStockApplication {

    public static void main(String[] args) {
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        final ViewStockDataAccessInterface viewStockDataAccessInterface = new ViewStockDataAccessInterface() {
            @Override
            public Stock getStock(String symbol) {
                return null;
            }
        };

        // Create a temporary implementation of FavoriteStockInputBoundary
        final FavoriteStockInputBoundary favoriteStockInputBoundary = new FavoriteStockInputBoundary() {
            @Override
            public void toggleFavorite(FavoriteStockInputData inputData) {
                // Implement temporary behavior or leave empty
            }

            @Override
            public void getFavorites() {
                // Implement temporary behavior or leave empty
            }
        };

        final ViewStockController viewStockController =
                ViewStockUseCaseFactory.create(
                        viewManagerModel,
                        viewStockDataAccessInterface,
                        favoriteStockInputBoundary
                );
        final ViewStockViewModel viewStockViewModel = new ViewStockViewModel();

        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame("Stock Analysis Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(Constants.MAIN_FRAME_DIMENSION);
            frame.setMinimumSize(Constants.MAIN_FRAME_MIN_DIMENSION);
            frame.setLocationRelativeTo(null);

            final ViewStockView viewStockView = new ViewStockView(viewStockViewModel, viewStockController);
            frame.add(viewStockView.getMainPanel());

            frame.setVisible(true);
        });
    }
}