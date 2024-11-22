package interface_adapters.view_stock;

import app.Constants;
import interface_adapters.ViewModel;

/**
 * View model for view_stock use case.
 */
public class ViewStockViewModel extends ViewModel<ViewStockState> {

    public ViewStockViewModel() {
        super(Constants.STOCK_VIEW);
        setState(new ViewStockState());
    }
}
