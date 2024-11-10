package main.java.interface_adapters.view_stock;

/**
 * The state for the Login View Model.
 */
public class ViewStockState {
    private String symbol = "";
    private String viewStockError;

    public String getSymbol() {
        return symbol;
    }

    public String getViewStockError() {
        return viewStockError;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setViewStockError(String symbolError) {
        this.viewStockError = symbolError;
    }

}
