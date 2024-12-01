package interface_adapters.view_stock;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import entities.MetricValues;
import entities.SharePrices;

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
    private SharePrices sharePrices;
    private MetricValues earnings;
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

    public SharePrices getSharePrices() {
        return sharePrices;
    }
  
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

    public void setSharePrices(SharePrices newSharePrices) {
        this.sharePrices = newSharePrices;
    }

    public void setEarnings(MetricValues newEarnings) {
        this.earnings = newEarnings;
    }

    public void setViewStockError(String newViewStockError) {
        this.viewStockError = newViewStockError;
    }

    /**
     * Adds a stock symbol to the favorites list.
     *
     * @param stockSymbol the stock symbol to add to favorites
     */
    public void addFavorite(String stockSymbol) {
        favorites.add(stockSymbol);
    }

    /**
     * Removes a stock symbol from the favorites list.
     *
     * @param stockSymbol the stock symbol to remove from favorites
     */
    public void removeFavorite(String stockSymbol) {
        favorites.remove(stockSymbol);
    }

    /**
     * Checks if a stock symbol is in the favorites list.
     *
     * @param stockSymbol the stock symbol to check
     * @return true if the stock is favorited, false otherwise
     */
    public boolean isFavorite(String stockSymbol) {
        return favorites.contains(stockSymbol);
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
