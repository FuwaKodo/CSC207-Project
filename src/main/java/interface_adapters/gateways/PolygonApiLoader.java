package interface_adapters.gateways;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.JSONObject;

/**
 * DAO (Data Access Object) for Stock API: Polygon.
 */
public class PolygonApiLoader implements ApiDataLoader {
    private static String apiKey = "NbKXkuoH3mdV4H6DN493zVQg0M2d0LlG";
    private static String baseUrl = "https://api.polygon.io/v1/";
    private static String apiCallLimitErrorMsg =
            "You've exceeded the maximum requests per minute, "
                    + "please wait or upgrade your subscription to continue. "
                    + "https://polygon.io/pricing";
    private static String statusKey = "status";
    private static String symbolKey = "symbol";
    private static String dateKey = "from";
    private static String messageKey = "message";

    @Override
    public JSONObject loadOneEntry(String stockSymbol, Date date) {
        // Implemented force take data
        final ArrayList<String> endpoint = new ArrayList<>();
        endpoint.add("open-close");
        endpoint.add(stockSymbol);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = dateFormat.format(date);
        endpoint.add(formattedDate);

        final SortedMap<String, String> queryParameters = new TreeMap<>();
        queryParameters.put("adjusted", "true");
        queryParameters.put("apiKey", apiKey);

        boolean apiIsDone = false;

        JSONObject jsonData = new JSONObject();

        while (!apiIsDone) {
            apiIsDone = true;
            try {
                final String apiUrl = buildApiUrl(baseUrl, endpoint, queryParameters);
                final HttpClient client = HttpClient.newHttpClient();
                final HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(apiUrl))
                        .build();
                final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                jsonData = new JSONObject(response.body());
            }

            catch (IOException | InterruptedException exception) {
                System.out.println("LoadOneEntry Server Issue");
            }

            // System.out.println(jsonData.toString());

            if (jsonData.has("error") && jsonData.get("error")
                    .equals(apiCallLimitErrorMsg)) {
                apiIsDone = false;
            }

            else if (jsonData.has(statusKey) && jsonData.get(statusKey).equals("NOT_FOUND")) {
                // System.out.println("activated");
                jsonData = createNotFoundJsonData(stockSymbol, formattedDate);
            }

            else if (jsonData.has(statusKey) && jsonData.get(statusKey).equals("OK")) {
                jsonData = createFoundJsonData(jsonData);
            }

            else {
                jsonData = createErrorJsonData(stockSymbol, formattedDate);
            }
        }

        jsonData.put("api", "polygon");

        return jsonData;
    }

    /**
     * Creates a JSON object representing a "Data not found" entry for a given stock symbol and date.
     *
     * <p>This method generates a JSON object with details indicating that data for the specified
     * stock symbol and date is not available. The JSON object includes the stock symbol,
     * the formatted date, a descriptive message, and a status field set to "NOT_FOUND".
     * </p>
     *
     * @param stockSymbol   the stock symbol for which data is missing (e.g., "AAPL" for Apple Inc.).
     * @param formattedDate the date in a formatted string (e.g., "2024-11-27") representing
     *                      when the data was queried or expected.
     * @return a {@link JSONObject} containing the following fields:
     *         <ul>
     *           <li><b>"symbol"</b>: the stock symbol.</li>
     *           <li><b>"from"</b>: the formatted date.</li>
     *           <li><b>"message"</b>: a descriptive message, "Data not found."</li>
     *           <li><b>"status"</b>: the status of the entry, set to "NOT_FOUND".</li>
     *         </ul>
     */
    public JSONObject createNotFoundJsonData(String stockSymbol, String formattedDate) {
        final JSONObject notFoundJson = new JSONObject();
        notFoundJson.put(symbolKey, stockSymbol);
        notFoundJson.put(dateKey, formattedDate);
        notFoundJson.put(messageKey, "Data not found.");
        notFoundJson.put(statusKey, "NOT_FOUND");
        return notFoundJson;
    }

    /**
     * Creates a JSON object representing a "Fatal error" entry for a given stock symbol and date.
     *
     * <p>This method generates a JSON object with details indicating a critical error occurred
     * for the specified stock symbol and date. The JSON object includes the stock symbol,
     * the formatted date, a descriptive message, and a status field set to "FATAL_ERROR".
     * </p>
     *
     * @param stockSymbol   the stock symbol associated with the error (e.g., "AAPL" for Apple Inc.).
     * @param formattedDate the date in a formatted string (e.g., "2024-11-27") representing
     *                      when the error occurred or was logged.
     * @return a {@link JSONObject} containing the following fields:
     *         <ul>
     *           <li><b>symbolKey</b>: the stock symbol key, mapped to the provided stock symbol.</li>
     *           <li><b>dateKey</b>: the date key, mapped to the provided formatted date.</li>
     *           <li><b>messageKey</b>: a descriptive message, "Fatal error."</li>
     *           <li><b>statusKey</b>: the status of the entry, set to "FATAL_ERROR".</li>
     *         </ul>
     */
    public JSONObject createErrorJsonData(String stockSymbol, String formattedDate) {
        final JSONObject errorJson = new JSONObject();
        errorJson.put(symbolKey, stockSymbol);
        errorJson.put(dateKey, formattedDate);
        errorJson.put(messageKey, "Fatal error.");
        errorJson.put(statusKey, "FATAL_ERROR");
        return errorJson;
    }

    /**
     * Creates a JSON object representing a "Data found" entry based on the provided JSON data.
     *
     * <p>This method constructs a new JSON object with fields populated from the input {@code jsonData}.
     * It indicates that data for a specific stock symbol and date has been successfully found.
     * The JSON object includes details such as stock symbol, date, status, and other stock-related metrics.
     * </p>
     *
     * @param jsonData a {@link JSONObject} containing the stock data to populate the new JSON object.
     *                 Expected keys in the input JSON object include:
     *                 <ul>
     *                   <li><b>"symbol"</b>: the stock symbol.</li>
     *                   <li><b>"date"</b>: the date in a formatted string (e.g., "2024-11-27").</li>
     *                   <li><b>"volume"</b>: the stock trading volume as a {@code double}.</li>
     *                   <li><b>"high"</b>: the stock's highest trading price as a {@code double}.</li>
     *                   <li><b>"preMarket"</b>: the pre-market trading price as a {@code double}.</li>
     *                   <li><b>"low"</b>: the stock's lowest trading price as a {@code double}.</li>
     *                   <li><b>"afterHours"</b>: the after-hours trading price as a {@code double}.</li>
     *                   <li><b>"close"</b>: the stock's closing price as a {@code double}.</li>
     *                   <li><b>"open"</b>: the stock's opening price as a {@code double}.</li>
     *                 </ul>
     * @return a {@link JSONObject} containing the following fields:
     *         <ul>
     *           <li><b>"symbol"</b>: the stock symbol.</li>
     *           <li><b>"date"</b>: the formatted date.</li>
     *           <li><b>"message"</b>: a descriptive message, "Data found."</li>
     *           <li><b>"status"</b>: the status of the entry, set to "OK".</li>
     *           <li><b>"volume"</b>: the stock trading volume.</li>
     *           <li><b>"high"</b>: the highest trading price.</li>
     *           <li><b>"preMarket"</b>: the pre-market trading price.</li>
     *           <li><b>"low"</b>: the lowest trading price.</li>
     *           <li><b>"afterHours"</b>: the after-hours trading price.</li>
     *           <li><b>"close"</b>: the closing price.</li>
     *           <li><b>"open"</b>: the opening price.</li>
     *         </ul>
     */
    public JSONObject createFoundJsonData(JSONObject jsonData) {
        final JSONObject foundJson = new JSONObject();
        foundJson.put(symbolKey, jsonData.getString("symbol"));
        foundJson.put(dateKey, jsonData.getString("from"));
        foundJson.put(messageKey, "Data found.");
        foundJson.put(statusKey, "OK");
        foundJson.put("volume", jsonData.getDouble("volume"));
        foundJson.put("high", jsonData.getDouble("high"));
        foundJson.put("preMarket", jsonData.getDouble("preMarket"));
        foundJson.put("low", jsonData.getDouble("low"));
        foundJson.put("afterHours", jsonData.getDouble("afterHours"));
        foundJson.put("close", jsonData.getDouble("close"));
        foundJson.put("open", jsonData.getDouble("open"));
        return foundJson;
    }

}
