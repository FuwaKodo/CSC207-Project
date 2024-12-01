package entities;

/**
 * Represents a favorite stock with its associated symbol and company name.
 */
public class FavoriteStock {

    /** The stock symbol (ticker) of the favorite stock. */
    private final String symbol;

    /** The company name associated with the favorite stock. */
    private final String company;

    /**
     * Constructs a FavoriteStock instance with the specified symbol and company name.
     *
     * @param symbol the stock symbol (ticker) of the favorite stock
     * @param company the name of the company associated with the stock
     */
    public FavoriteStock(String symbol, String company) {
        this.symbol = symbol;
        this.company = company;
    }

    /**
     * Returns the stock symbol of this favorite stock.
     *
     * @return the stock symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the company name of this favorite stock.
     *
     * @return the company name
     */
    public String getCompany() {
        return company;
    }
}
