package interface_adapters.view_stock;

import java.util.*;

/**
 * Represents the state of the View Model for viewing stock information,
 * including stock symbols, company names, share prices, earnings,
 * errors, and favorite stocks.
 */
public class ViewStockState {
    /** The stock symbol being viewed. */
    private String symbol = "";

    /** The company name associated with the stock symbol. */
    private String company = "";

    /** A list of share prices for the stock over time. */
    private List<Double> sharePrices = new ArrayList<>();

    /** A list of earnings for the stock over time. */
    private List<Double> earnings = new ArrayList<>();

    /** An optional error message related to viewing stock information. */
    private String viewStockError;

    /** A set of favorite stock symbols. */
    private Set<String> favorites = new HashSet<>();

    /**
     * Returns the current stock symbol.
     *
     * @return the stock symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the current company name.
     *
     * @return the company name
     */
    public String getCompany() {
        return company;
    }

    /**
     * Returns the list of share prices.
     *
     * @return the list of share prices
     */
    public List<Double> getSharePrices() {
        return sharePrices;
    }

    /**
     * Returns a list of share prices for the specified number of days back in time.
     *
     * @param daysBack the number of days to backtrack
     * @return a list of share prices within the specified time period
     */
    public List<Double> getSharePrices(int daysBack) {
        return sharePrices.subList(Math.max(0, sharePrices.size() - daysBack), sharePrices.size());
    }

    /**
     * Returns the list of earnings.
     *
     * @return the list of earnings
     */
    public List<Double> getEarnings() {
        return earnings;
    }

    /**
     * Returns a list of earnings for the specified number of days back in time.
     *
     * @param daysBack the number of days to backtrack
     * @return a list of earnings within the specified time period
     */
    public List<Double> getEarnings(int daysBack) {
        return earnings.subList(Math.max(0, earnings.size() - daysBack), earnings.size());
    }

    /**
     * Returns the current view stock error message.
     *
     * @return the error message, or null if there is no error
     */
    public String getViewStockError() {
        return viewStockError;
    }

    /**
     * Sets the stock symbol to be viewed.
     *
     * @param symbol the stock symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Sets the company name associated with the stock symbol.
     *
     * @param company the company name to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Sets the list of share prices.
     *
     * @param sharePrices the list of share prices to set
     */
    public void setSharePrices(List<Double> sharePrices) {
        this.sharePrices = sharePrices;
    }

    /**
     * Sets the list of earnings.
     *
     * @param earnings the list of earnings to set
     */
    public void setEarnings(List<Double> earnings) {
        this.earnings = earnings;
    }

    /**
     * Sets the view stock error message.
     *
     * @param symbolError the error message to set
     */
    public void setViewStockError(String symbolError) {
        this.viewStockError = symbolError;
    }

    /**
     * Adds a stock symbol to the favorites list.
     *
     * @param symbol the stock symbol to add to favorites
     */
    public void addFavorite(String symbol) {
        favorites.add(symbol);
    }

    /**
     * Removes a stock symbol from the favorites list.
     *
     * @param symbol the stock symbol to remove from favorites
     */
    public void removeFavorite(String symbol) {
        favorites.remove(symbol);
    }

    /**
     * Checks if a stock symbol is in the favorites list.
     *
     * @param symbol the stock symbol to check
     * @return true if the stock is favorited, false otherwise
     */
    public boolean isFavorite(String symbol) {
        return favorites.contains(symbol);
    }

    /**
     * Returns an unmodifiable set of all favorite stock symbols.
     *
     * @return an unmodifiable set of favorite stock symbols
     */
    public Set<String> getFavorites() {
        return Collections.unmodifiableSet(favorites);
    }
}