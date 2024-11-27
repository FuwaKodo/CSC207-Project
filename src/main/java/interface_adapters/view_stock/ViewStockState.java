package interface_adapters.view_stock;

import java.util.*;

/**
 * The state for the Login View Model.
 */
public class ViewStockState {
    private String symbol = "";
    private String company = "";
    private List<Double> sharePrices = new ArrayList<>();
    private List<Double> earnings = new ArrayList<>();
    private String viewStockError;
    private Set<String> favorites = new HashSet<>();

    public String getSymbol() {
        return symbol;
    }

    public String getCompany() {
        return company;
    }

    public List<Double> getSharePrices() {
        return sharePrices;
    }

    /**
     * Return a list of share prices for the given days back in time.
     * @param daysBack number of days to back track
     * @return list of share prices in given time period
     */
    public List<Double> getSharePrices(int daysBack) {
        return sharePrices.subList(sharePrices.size() - daysBack, sharePrices.size());
    }

    public List<Double> getEarnings() {
        return earnings;
    }

    /**
     * Return a list of earnings for the given days back in time.
     * @param daysBack number of days to back track
     * @return list of earnings in given time period
     */
    public List<Double> getEarnings(int daysBack) {
        return earnings.subList(earnings.size() - daysBack, earnings.size());
    }

    public String getViewStockError() {
        return viewStockError;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setSharePrices(List<Double> sharePrices) {
        this.sharePrices = sharePrices;
    }

    public void setEarnings(List<Double> earnings) {
        this.earnings = earnings;
    }

    public void setViewStockError(String symbolError) {
        this.viewStockError = symbolError;
    }

    /**
     * Add a stock symbol to favorites.
     * @param symbol the stock symbol to add
     */
    public void addFavorite(String symbol) {
        favorites.add(symbol);
    }

    /**
     * Remove a stock symbol from favorites.
     * @param symbol the stock symbol to remove
     */
    public void removeFavorite(String symbol) {
        favorites.remove(symbol);
    }

    /**
     * Check if a stock symbol is favorited.
     * @param symbol the stock symbol to check
     * @return true if the stock is favorited, false otherwise
     */
    public boolean isFavorite(String symbol) {
        return favorites.contains(symbol);
    }

    /**
     * Get all favorite stock symbols.
     * @return an unmodifiable set of favorite stock symbols
     */
    public Set<String> getFavorites() {
        return Collections.unmodifiableSet(favorites);
    }
}