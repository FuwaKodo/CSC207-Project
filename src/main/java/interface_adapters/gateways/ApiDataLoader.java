package interface_adapters.gateways;

import java.util.*;

import entities.MetricValues;
import entities.SharePrices;
import org.json.JSONObject;

/**
 * Stores relevant data for creating a stock entity.
 */
public interface ApiDataLoader {

    /**
     * Get company name.
     * @return the name of the company
     */
    String getCompany();

    /**
     * Get stock symbol.
     * @return the symbol of the stock
     */
    String getSymbol();

    /**
     * Get historical share prices of a stock.
     * @return list of share prices
     */
    SharePrices getSharePrices();

    /**
     * Get historical earnings of a stock.
     * @return list of earnings
     */
    MetricValues getEarnings();

    /**
     * Clears the time fields of the given Calendar object, setting the time to midnight (00:00:00.000).
     * @param calendar the Calendar object to modify. Must not be null.
     */
    default void clearTimeFields(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * Sets the given Calendar object to the specified day and resets the time to midnight (00:00:00.000).
     * @param calendar the Calendar object to modify. Must not be null.
     * @param year the year to set (e.g., 2024).
     * @param month the month to set (0-based, e.g., 0 for January, 11 for December).
     * @param day the day of the month to set (1-based, e.g., 1 for the first day of the month).
     * @return Date object representing the given dates on midnight
     */
    default Date setDay(Calendar calendar, int year, int month, int day) {
        calendar.set(year, month, day);
        clearTimeFields(calendar);
        return calendar.getTime();
    }

    /**
     * Adds the specified number of days to the given Calendar object and resets the time to midnight.
     * @param calendar the Calendar object to modify. Must not be null.
     * @param days the number of days to add. Can be positive or negative.
     * @return Date object representing the updated day after adding specified number of days
     */
    default Date addDay(Calendar calendar, int days) {
        calendar.add(Calendar.DATE, days);
        clearTimeFields(calendar);
        return calendar.getTime();
    }

    /**
     * Get volumes of a stock from a given start date to end date and stock symbol.
     * @return volumes of stock
     */
    MetricValues getVolumes();

    /**
     * Retrieves the volume of a stock for a specific stock symbol within a given date range.
     *
     * @param stockSymbol the symbol of the stock (e.g., "AAPL" for Apple Inc.).
     * @param startDate the start date of the date range (inclusive).
     * @param endDate the end date of the date range (inclusive).
     * @return an object containing the volume data for the stock within the specified date range,
     *         or {@code null} if the input is invalid (e.g., startDate is after endDate,
     *         or either date is null).
     */
    MetricValues getVolumes(String stockSymbol, Date startDate, Date endDate);

    /**
     * Generate API url from endpoint and query parameters.
     * @param baseUrl root address where the API is hosted.
     * @param endpoint relative path to the resource.
     * @param queryParameters additional parameters to modify request.
     * @return API url to interact with server's API
     */
    default String buildApiUrl(String baseUrl, ArrayList<String> endpoint, SortedMap<String, String> queryParameters) {
        // Generalized function
        final StringBuilder apiUrl = new StringBuilder(baseUrl);
        for (String endpointData : endpoint) {
            apiUrl.append(endpointData);
            apiUrl.append("/");
        }

        if (!endpoint.isEmpty()) {
            apiUrl.setLength(apiUrl.length() - 1);
        }

        apiUrl.append("?");

        for (Map.Entry<String, String> queryData : queryParameters.entrySet()) {
            final String queryKey = queryData.getKey();
            final String queryValue = queryData.getValue();
            apiUrl.append(queryKey).append("=").append(queryValue).append("&");
        }

        apiUrl.setLength(apiUrl.length() - 1);

        return apiUrl.toString();
    }

    /**
     * Get volume of a stock from a specific date and stock symbol.
     * @param stockSymbol the symbol of the stock.
     * @param date the specific date for which the volume is needed.
     * @return volume of stock
     */
    Double getVolume(String stockSymbol, Date date);

    /**
     * Request to a given API to return a JSONObject corresponding to the StockSymbol and Date. Note the value
     * is already formatted in a way where if the object is succesful:
     * Example
     * and if it fails it should say msg = MISSING_INFORMATION
     * @param stockSymbol yea
     * @param date yea
     * @return jsonObject
     */
    JSONObject loadOneEntry(String stockSymbol, Date date);
}
