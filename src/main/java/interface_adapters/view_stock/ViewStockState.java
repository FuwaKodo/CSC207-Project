package interface_adapters.view_stock;

import entities.MetricValues;
import entities.SharePrices;

import java.util.ArrayList;
import java.util.List;

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