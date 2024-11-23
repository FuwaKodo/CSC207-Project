package interface_adapters.gateways;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

import entities.MetricValues;
import entities.SharePrices;

/**
 * Stores relevant data for creating a stock entity.
 */
public interface StockDataLoader {

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
     * Get volumes of a stock.
     * @return volumes of stock
     */
    MetricValues getVolumes();

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

        // Under the assumption that the queryParameters exist
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
}
