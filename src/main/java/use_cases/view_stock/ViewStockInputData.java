package use_cases.view_stock;

/**
 * Input data for viewing a stock.
 */
public class ViewStockInputData {
    private final String symbol;

    public ViewStockInputData(String symbol) {
        this.symbol = symbol.strip().toUpperCase();
    }

    String getSymbol() {
        return symbol;
    }
}
