package main.java.entities;

/**
 * Stock: the stocks of a single company.
 */
public interface Stock {

    /**
     * Get the name of the company.
     * @return The company behind the stock.
     */
    public String getCompany();

    /**
     * Get the symbol of the stock.
     * @return The symbol of the stock.
     */
    public String getSymbol();

    /**
     * Get share price at a specific day.
     * @param day the number of days before today
     * @return share price
     */
    public Double getSharePrice(int day);
}
