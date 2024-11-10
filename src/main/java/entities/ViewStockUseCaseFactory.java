package main.java.entities;

import main.java.interface_adapters.ViewManagerModel;
import main.java.interface_adapters.view_stock.*;
import main.java.use_cases.view_stock.ViewStockDataAccessInterface;
import main.java.use_cases.view_stock.ViewStockInputBoundary;
import main.java.use_cases.view_stock.ViewStockInteractor;

/**
 * Factory class responsible for creating and assembling all components needed for the view stock use case.
 * This includes creating the interactor, presenter, view model, and data access objects
 */
public class ViewStockUseCaseFactory {

    /**
     * Creates all components for the view stock use case and returns a configured controller.
     * This method wires up all the dependencies following clean architecture principles.
     *
     * @param viewManagerModel The view manager model that handles view switching
     * @param dataAccessObject The concrete implementation of StockDataLoader to use
     * @return A configured ViewStockController ready for use
     */
    public static ViewStockController create(
            ViewManagerModel viewManagerModel,
            ViewStockDataAccessInterface dataAccessObject) {

        // Create the view model and state
        ViewStockViewModel viewStockViewModel = new ViewStockViewModel();

        // Create the presenter with its required dependencies
        ViewStockPresenter viewStockPresenter = new ViewStockPresenter(viewManagerModel, viewStockViewModel);

        // Create the interactor with its dependencies
        ViewStockInputBoundary viewStockInteractor = new ViewStockInteractor(
                viewStockPresenter,
                dataAccessObject
        );

        // Create and return the controller
        return new ViewStockController(viewStockInteractor);
    }
}