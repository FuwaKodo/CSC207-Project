package interface_adapters.gateways;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONObject;

import entities.MetricValues;
import entities.SharePrices;

/**
 * DAO (Data Access Object) for Stock Api: Polygon.
 */
public class PolygonApiLoader implements StockDataLoader {
    // Cache idea maybe not applicable
    // private static ArrayList<Double> cacheVolume = new ArrayList<>();
    // private static ArrayList<Double> cachePreMarket = new ArrayList<>();
    // private static ArrayList<Date> cacheDate = new ArrayList<>();

    private static String apiKey = "NbKXkuoH3mdV4H6DN493zVQg0M2d0LlG";
    private static String baseUrl = "https://api.polygon.io/v1/";

    @Override
    public String getCompany() {
        return "";
    }

    @Override
    public String getSymbol() {
        return "";
    }

    @Override
    public SharePrices getSharePrices() {
        return null;
    }

    @Override
    public MetricValues getEarnings() {
        return null;
    }

    @Override
    public MetricValues getVolumes() {
        // TODO: Remove the getVolumes with no parameters
        return null;
    }

    @Override
    public MetricValues getVolumes(String stockSymbol, Date startDate, Date endDate) {
        // TODO: stockSymbol is assumed to always exist
        MetricValues volumeData = null;
        if (startDate != null && endDate != null && startDate.compareTo(endDate) <= 0) {
            final ArrayList<Double> volumeValues = new ArrayList<>();
            final ArrayList<Date> volumeDates = new ArrayList<>();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            clearTimeFields(calendar);
            while (!calendar.getTime().after(endDate)) {
                // Adding delay because API can't handle the amount of calls
                try {
                    final Date currentDate = calendar.getTime();
                    // Sleep amount
                    Thread.sleep(10000);
                    volumeValues.add(getVolume(stockSymbol, currentDate));
                    volumeDates.add(currentDate);
                    addDay(calendar, 1);
                }

                catch (InterruptedException interruptedException) {
                    volumeData = null;
                    System.out.println("Sleep is interrupted");
                }
            }

            System.out.println(volumeValues);
            System.out.println(volumeDates);

            volumeData = new MetricValues(volumeValues, volumeDates);
        }

        return volumeData;
    }

    /* Part of cache idea
    private static void clearTimeFieldsStatic(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
    */

    /* Part of cache idea
    public static void updateCaches(String stockSymbol, Date startDate, Date endDate) {
        // TODO: stockSymbol is assumed to always exist
        if (startDate != null && endDate != null && startDate.compareTo(endDate) <= 0) {
            // final ArrayList<Double> volumeValues = new ArrayList<>();
            // final ArrayList<Date> volumeDates = new ArrayList<>();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            clearTimeFieldsStatic(calendar);
            while (!calendar.getTime().after(endDate)) {
                // Adding delay because API can't handle the amount of calls
                try {
                    final Date currentDate = calendar.getTime();
                    if (!cacheDate.contains(currentDate)) {
                        // Update caches
                        // TODO: Maybe sort this if necessary
                        cacheVolume.add(getVolumeStatic(stockSymbol, currentDate));
                        cacheDate.add(currentDate);
                        addDay(calendar, 1);
                        Thread.sleep(8000);
                    }
                }

                catch (InterruptedException e) {
                    volumeData = null;
                    System.out.println("Sleep is interrupted");
                }
            }

            System.out.println(volumeValues);
            System.out.println(volumeDates);

            volumeData = new MetricValues(volumeValues, volumeDates);
        }

        return volumeData;
    }
     */

    @Override
    public Double getVolume(String stockSymbol, Date date) {
        final ArrayList<String> endpoint = new ArrayList<>();
        endpoint.add("open-close");
        endpoint.add(stockSymbol);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = dateFormat.format(date);
        endpoint.add(formattedDate);

        final SortedMap<String, String> queryParameters = new TreeMap<>();
        queryParameters.put("adjusted", "true");
        queryParameters.put("apiKey", apiKey);

        Double volumeValue = Double.NaN;
        JSONObject jsonData;

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
            jsonData = null;
            System.out.println("Server Issue");
        }

        System.out.println(jsonData.toString());

        if (jsonData != null && jsonData.has("volume")) {
            volumeValue = jsonData.getDouble("volume");
        }

        return volumeValue;
    }

    /* Part of cache idea
    public static Double getVolumeStatic(String stockSymbol, Date date) {
        final ArrayList<String> endpoint = new ArrayList<>();
        endpoint.add("open-close");
        endpoint.add(stockSymbol);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String formattedDate = dateFormat.format(date);
        endpoint.add(formattedDate);

        final SortedMap<String, String> queryParameters = new TreeMap<>();
        queryParameters.put("adjusted", "true");
        queryParameters.put("apiKey", apiKey);

        Double volumeValue = Double.NaN;
        JSONObject jsonData;

        try {
            final String apiUrl = buildApiUrlStatic(endpoint, queryParameters);
            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .build();
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonData = new JSONObject(response.body());
        }

        catch (IOException | InterruptedException exception) {
            jsonData = null;
            System.out.println("Server Issue");
        }

        System.out.println(jsonData.toString());

        if (jsonData != null && jsonData.has("volume")) {
            volumeValue = jsonData.getDouble("volume");
        }

        return volumeValue;
    }
     */

    /* Part of cache idea
    private static String buildApiUrlStatic(ArrayList<String> endpoint, SortedMap<String, String> queryParameters) {
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
     */

    /* Part of cache idea
    private static Date addDayStatic(Calendar calendar, int days) {
        calendar.add(Calendar.DATE, days);
        clearTimeFields(calendar);
        return calendar.getTime();
    }
     */
}
