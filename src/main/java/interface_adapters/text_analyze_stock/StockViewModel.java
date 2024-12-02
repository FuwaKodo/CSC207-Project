package interface_adapters.text_analyze_stock;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel to hold stock analysis data for the view.
 */
public class StockViewModel {

    private String stockName;
    private double currentPrice;
    private final List<Double> projectedPrices = new ArrayList<>();
    private String action;

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public List<Double> getProjectedPrices() {
        return projectedPrices;
    }

    /**
     * Sets the projected prices for year 1, year 2, and year 3.
     * @param prices The list of the prices.
     */
    public void setProjectedPrices(double... prices) {
        projectedPrices.clear();
        for (double price : prices) {
            projectedPrices.add(price);
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
