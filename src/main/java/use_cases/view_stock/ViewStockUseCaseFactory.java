package use_cases.view_stock;

import interface_adapters.ViewManagerModel;
import interface_adapters.view_stock.ViewStockController;
import interface_adapters.view_stock.ViewStockPresenter;
import interface_adapters.view_stock.ViewStockViewModel;
import use_cases.favorites.FavoriteStockInputBoundary;

public class ViewStockUseCaseFactory {
    public static ViewStockController create(
            ViewManagerModel viewManagerModel,
            ViewStockDataAccessInterface dataAccessObject,
            FavoriteStockInputBoundary favoriteStockUseCaseInteractor) {

        // Create the view model and state
        final ViewStockViewModel viewStockViewModel = new ViewStockViewModel();

        // Create the presenter with its required dependencies
        final ViewStockPresenter viewStockPresenter = new ViewStockPresenter(
                viewManagerModel,
                viewStockViewModel
        );

        // Create the interactor with its dependencies
        final ViewStockInputBoundary viewStockInteractor = new ViewStockInteractor(
                viewStockPresenter,
                dataAccessObject
        );

        // Create and return the controller with both required dependencies
        return new ViewStockController(
                viewStockInteractor,
                favoriteStockUseCaseInteractor
        );
    }
}