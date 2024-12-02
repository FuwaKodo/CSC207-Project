package interface_adapters.view_stock;

import java.util.Calendar;
import java.util.Date;

import app.Constants;
import use_cases.view_stock.ViewStockInputBoundary;
import use_cases.view_stock.ViewStockInputData;

/**
 * Controller for the view stock use case and favorites management.
 */
public class ViewStockController {

    private final ViewStockInputBoundary viewStockUseCaseInteractor;

    public ViewStockController(
            ViewStockInputBoundary viewStockUseCaseInteractor) {
        this.viewStockUseCaseInteractor = viewStockUseCaseInteractor;
    }

    /**
     * Executes the view stock use case.
     * @param stockSymbol the symbol of the stock
     * @param startDate start date
     * @param endDate end date
     */
    public void execute(String stockSymbol, Date startDate, Date endDate) {
        final ViewStockInputData viewStockInputData = new ViewStockInputData(stockSymbol, startDate, endDate);
        viewStockUseCaseInteractor.execute(viewStockInputData);
    }

    /**
     * Executes the view stock use case.
     * @param stockSymbol the symbol of the stock
     */
    public void execute(String stockSymbol) {
        // Calculate today's date
        final Date endDate = new Date();
        // Calculate 15 days before today
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR, -Constants.HALF_MONTH);
        final Date startDate = calendar.getTime();

        // Create the input data and execute the use case
        final ViewStockInputData viewStockInputData = new ViewStockInputData(stockSymbol, startDate, endDate);
        viewStockUseCaseInteractor.execute(viewStockInputData);
    }

}
