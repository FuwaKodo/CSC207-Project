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

import entities.MetricValues;
import entities.SharePrices;

/**
 * DAO (Data Access Object) for Stock Api: Polygon.
 */
public class PolygonApiLoader implements StockDataLoader {
    private final String apiKey = "NbKXkuoH3mdV4H6DN493zVQg0M2d0LlG";
    private final String baseUrl = "https://api.polygon.io/v1/";

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
        return null;
    }

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
        }

        if (jsonData != null && jsonData.has("volume")) {
            volumeValue = jsonData.getDouble("volume");
        }

        return volumeValue;
    }
}
