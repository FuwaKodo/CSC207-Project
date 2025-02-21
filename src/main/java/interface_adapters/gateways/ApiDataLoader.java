package interface_adapters.gateways;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

import org.json.JSONObject;

/**
 * Stores relevant data for creating a stock entity.
 */
public interface ApiDataLoader {
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
     * Loads a single stock data entry for a specified stock symbol and date using an API call.
     *
     * <p>This method constructs an API request to fetch stock data for a given stock symbol and date.
     * It handles the response, including cases of API call limits, missing data, successful retrieval,
     * and errors. The returned JSON object includes the processed stock data or an error indicator.
     * </p>
     *
     * @param stockSymbol the stock symbol for which data is to be retrieved (e.g., "AAPL" for Apple Inc.).
     * @param date        the {@link Date} object representing the date for the stock data.
     * @return a {@link JSONObject} containing the retrieved data or a generated response in the following cases:
     *         <ul>
     *           <li>If the data is successfully retrieved, a JSON object with the stock data is returned.</li>
     *           <li>If the data is missing, a "NOT_FOUND" JSON object is returned,generated
     *           by {@code createNotFoundJsonData}.</li>
     *           <li>If an API call limit is hit, the method retries until the data is retrieved or
     *           an error occurs.</li>
     *           <li>If an error occurs, a "FATAL_ERROR" JSON object is returned, generated
     *           by {@code createErrorJsonData}.</li>
     *         </ul>
     *         The returned JSON object always includes an additional field:
     *         <ul>
     *           <li><b>"api"</b>: the API provider, set to "polygon".</li>
     *         </ul>
     * @throws RuntimeException if the API URL cannot be built or if a critical issue occurs during the API request.
     */
    JSONObject loadOneEntry(String stockSymbol, Date date);
}
