package interface_adapters.view_stock;

import use_cases.view_stock.ViewStockInputBoundary;
import use_cases.view_stock.ViewStockInputData;
import use_cases.favorites.FavoriteStockInputBoundary;
import use_cases.favorites.FavoriteStockInputData;

/**
 * Controller for the view stock use case and favorites management.
 */
public class ViewStockController {

    private final ViewStockInputBoundary viewStockUseCaseInteractor;
    private final FavoriteStockInputBoundary favoriteStockUseCaseInteractor;

    public ViewStockController(
            ViewStockInputBoundary viewStockUseCaseInteractor,
            FavoriteStockInputBoundary favoriteStockUseCaseInteractor) {
        this.viewStockUseCaseInteractor = viewStockUseCaseInteractor;
        this.favoriteStockUseCaseInteractor = favoriteStockUseCaseInteractor;
    }

    /**
     * Executes the view stock use case.
     * @param stockSymbol the symbol of the stock
     */
    public void execute(String stockSymbol) {
        final ViewStockInputData viewStockInputData = new ViewStockInputData(stockSymbol);
        viewStockUseCaseInteractor.execute(viewStockInputData);
    }

}