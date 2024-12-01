package use_cases;

import java.util.Date;

import entities.MetricValues;
import entities.SharePrices;

/**
 * Stores relevant methods to get information about the stocks.
 */
public interface StockDataInterface {
    /**
     * Retrieves the trading volume for a specific company identified by its stock symbol on a given date.
     *
     * <p>The method first checks if the requested stock data is present in the existing file.
     * If found, it parses the file to retrieve the volume value. If the data is not found locally,
     * the method updates the stock data by fetching it from the gateway and retrieves the volume
     * value from the updated entry.</p>
     *
     * @param stockSymbol the unique stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param date the date for which the trading volume is requested.
     * @return the trading volume as a {@link Double} value. Returns {@link Double#NaN}
     *         if the data is unavailable or an I/O error occurs while reading the file.
     */
    Double getVolume(String stockSymbol, Date date);

    /**
     * Retrieves the trading volumes for a specific stock symbol over a specified date range.
     *
     * <p>This method iterates through all the dates between the given start and end dates (inclusive),
     * fetching the trading volume for each date. The volumes and corresponding dates are then stored
     * in a {@link MetricValues} object, which is returned as the result.</p>
     *
     * @param stockSymbol the stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param startDate   the start date of the date range (inclusive).
     * @param endDate     the end date of the date range (inclusive).
     * @return a {@link MetricValues} object containing:
     *         <ul>
     *             <li>A list of trading volume values for each date in the range.</li>
     *             <li>A list of corresponding dates for the volume values.</li>
     *         </ul>
     *         Returns {@code null} if the start date or end date is {@code null}, or if the start date
     *         is after the end date.
     */
    MetricValues getVolumes(String stockSymbol, Date startDate, Date endDate);

    /**
     * Retrieves the after-hours trading value for a specific company identified by its stock symbol on a given date.
     *
     * <p>This method checks whether the after-hours trading data for the specified stock symbol and date is
     * available in the local JSON file. If the entry exists and is valid, it extracts the "afterHours" value.
     * If the entry is not found, the method updates the stock data by fetching a new entry from the gateway
     * and then retrieves the "afterHours" value from the updated entry.</p>
     *
     * <p>Entries are validated using {@code isValidEntry} to ensure they are relevant (e.g., not from non-trading
     * days or invalid stock symbols).</p>
     *
     * @param stockSymbol the unique stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param date the date for which the after-hours trading value is requested.
     * @return the after-hours trading value as a {@link Double}. Returns {@link Double#NaN} if the data is
     *         unavailable or invalid.
     */
    Double getAfterHour(String stockSymbol, Date date);

    /**
     * Retrieves the after-hours trading values for a specific stock symbol over a specified date range.
     *
     * <p>This method iterates through all the dates between the given start and end dates (inclusive),
     * fetching the after-hours trading value for each date using {@link #getAfterHour(String, Date)}.
     * The after-hours values and corresponding dates are stored in a {@link MetricValues} object, which
     * is returned as the result.</p>
     *
     * @param stockSymbol the stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param startDate   the start date of the date range (inclusive).
     * @param endDate     the end date of the date range (inclusive).
     * @return a {@link MetricValues} object containing:
     *         <ul>
     *             <li>A list of after-hours trading values for each date in the range.</li>
     *             <li>A list of corresponding dates for the after-hours values.</li>
     *         </ul>
     *         Returns {@code null} if the start date or end date is {@code null}, or if the start date
     *         is after the end date.
     */
    MetricValues getAfterHours(String stockSymbol, Date startDate, Date endDate);

    /**
     * Retrieves the premarket trading value for a specific company identified by its stock symbol on a given date.
     *
     * <p>The method first checks whether the requested stock data is present in the local JSON file.
     * If found, it parses the file to retrieve the "preMarket" value. If the data is not found locally,
     * the method updates the stock data by fetching it from the gateway and retrieves the "preMarket" value
     * from the newly created entry.</p>
     *
     * <p>Entries are validated using {@code isValidEntry} to ensure they are relevant (e.g., not from
     * non-trading days or invalid stock symbols).</p>
     *
     * @param stockSymbol the unique stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param date        the date for which the premarket trading value is requested.
     * @return the premarket trading value as a {@link Double}. Returns {@link Double#NaN} if the data is
     *         unavailable or invalid.
     */
    Double getPremarket(String stockSymbol, Date date);

    /**
     * Retrieves the premarket trading values for a specific stock symbol over a specified date range.
     *
     * <p>This method iterates through all the dates between the given start and end dates (inclusive),
     * fetching the premarket trading value for each date using {@link #getPremarket(String, Date)}.
     * The premarket values and corresponding dates are stored in a {@link MetricValues} object, which
     * is returned as the result.</p>
     *
     * @param stockSymbol the stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param startDate   the start date of the date range (inclusive).
     * @param endDate     the end date of the date range (inclusive).
     * @return a {@link MetricValues} object containing:
     *         <ul>
     *             <li>A list of premarket trading values for each date in the range.</li>
     *             <li>A list of corresponding dates for the premarket values.</li>
     *         </ul>
     *         Returns {@code null} if the start date or end date is {@code null}, or if the start date
     *         is after the end date.
     */
    MetricValues getPremarkets(String stockSymbol, Date startDate, Date endDate);

    /**
     * Retrieves the share price data (open, close, high, and low prices) for a specific stock symbol on a given date.
     *
     * <p>This method retrieves the stock data for the specified stock symbol and date from a local JSON file
     * or fetches it from the gateway if the data does not exist locally. The method validates the entry to
     * ensure it is relevant (e.g., not from non-trading days or invalid stock symbols) before extracting the
     * open, close, high, and low prices.</p>
     *
     * <p>The extracted data is returned as a {@link SharePrices} object, which contains:
     * <ul>
     *     <li>A list of dates (one in this case).</li>
     *     <li>Lists of open, close, high, and low prices corresponding to the date.</li>
     * </ul></p>
     *
     * @param stockSymbol the unique stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param date        the date for which the share price data is requested.
     * @return a {@link SharePrices} object containing:
     *         <ul>
     *             <li>The date of the requested stock data.</li>
     *             <li>The open, close, high, and low prices for the stock on the given date.</li>
     *         </ul>
     *         If the data is unavailable or the entry is invalid, the prices are set to {@link Double#NaN}.
     */
    SharePrices getSharePrice(String stockSymbol, Date date);

    /**
     * Retrieves the share prices (open, close, high, and low) for a specific stock symbol over a given date range.
     *
     * <p>This method iterates through all the dates in the specified range (inclusive) and aggregates the
     * share prices for each day into a {@link SharePrices} object. The resulting {@link SharePrices} object
     * contains lists of dates and their corresponding open, close, high, and low prices.</p>
     *
     * @param stockSymbol the stock symbol representing the company (e.g., "AAPL" for Apple Inc.).
     * @param startDate   the start date of the date range (inclusive).
     * @param endDate     the end date of the date range (inclusive).
     * @return a {@link SharePrices} object containing:
     *         <ul>
     *             <li>Lists of open, close, high, and low prices for the specified date range.</li>
     *             <li>A list of corresponding dates for the retrieved prices.</li>
     *         </ul>
     *         Returns {@code null} if the date range is invalid (e.g., {@code startDate} or {@code endDate}
     *         is {@code null}).
     */
    SharePrices getSharePrices(String stockSymbol, Date startDate, Date endDate);
}
