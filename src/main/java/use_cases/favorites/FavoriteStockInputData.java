// use_cases/favorites/FavoriteStockInputData.java
package use_cases.favorites;

public class FavoriteStockInputData {
    private final String stockSymbol;

    public FavoriteStockInputData(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }
}