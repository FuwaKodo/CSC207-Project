package entities;

import java.time.LocalDate;

import interface_adapters.gateways.StockDataLoader;

/**
 * Stock: the stocks of a single company.
 */
public class Stock {
    private final String company;
    private final String symbol;
    private final Metrics metrics;
    private boolean isFavorite;

    public Stock(StockDataLoader loader, String company, String symbol) {
        // TODO: Issue here because getCompany no longer possible!
        // Getting symbol and company must be obtained from different instantiation
        this.symbol = symbol;
        this.company = company;
        metrics = null;
    }

    public String getCompany() {
        return company;
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * Get share price at a specific date.
     * @param date the date. If there is no value for the date, return the
     *             value on the date immediately after it.
     * @return share price
     */
    public Double getSharePrice(LocalDate date) {
        return metrics.getSharePrice(date);
    }

    /**
     * Get volume on a date.
     * @param date the date. If there is no value for the date, return
     *             the value on the date immediately after it.
     * @return volume
     */
    public Double getVolume(LocalDate date) {
        return metrics.getVolume(date);
    }

    /**
     * Return the growth percentage over a time interval.
     * @param start starting date of the interval, inclusive.
     * @param end end date of the interval, inclusive.
     * @return growth percentage from 0 to 1.
     */
    public Double getGrowthPercentage(LocalDate start, LocalDate end) {
        return metrics.getGrowthPercentage(start, end);
    }

    /**
     * Return the earnings per share over a time interval.
     * @param start starting date of the interval, inclusive.
     * @param end ending date of the interval, inclusive.
     * @return earnings per share
     */
    public Double getEarningsPerShare(LocalDate start, LocalDate end) {
        return metrics.getEarningsPerShare(start, end);
    }

    /**
     * Get dividends per share on a date.
     * @param date the date
     * @return dividends per share
     */
    public Double getDividendsPerShare(LocalDate date) {
        return metrics.getDividendsPerShare(date);
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
