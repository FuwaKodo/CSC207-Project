package use_cases.view_stock;

import interface_adapters.ViewManagerModel;
import interface_adapters.view_stock.ViewStockController;
import interface_adapters.view_stock.ViewStockPresenter;
import interface_adapters.view_stock.ViewStockViewModel;

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
     * @param viewStockViewModel The view model for the view stock use case
     * @param dataAccessObject The concrete implementation of ViewStockDataAccessInterface to use
     * @return A configured ViewStockController ready for use
     */
    public static ViewStockController create(
            ViewManagerModel viewManagerModel,
            ViewStockViewModel viewStockViewModel,
            ViewStockDataAccessInterface dataAccessObject) {

        // Create the presenter with its required dependencies
        final ViewStockPresenter viewStockPresenter = new ViewStockPresenter(viewManagerModel, viewStockViewModel);

        // Create the interactor with its dependencies
        final ViewStockInputBoundary viewStockInteractor = new ViewStockInteractor(
                viewStockPresenter,
                dataAccessObject
        );

        // Create and return the controller
        return new ViewStockController(viewStockInteractor);
    }
}
