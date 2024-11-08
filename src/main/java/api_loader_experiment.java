// Important Java Imports for API calls
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class api_loader_experiment {
    public static void main(String[] args) {
        try {
            // Define the API endpoint
            String[] listOfApi =
                    {
                            "https://api.polygon.io/v1/open-close/INTC/2024-11-06?adjusted=true&apiKey=NbKXkuoH3mdV4H6DN493zVQg0M2d0LlG",
                            "https://api.polygon.io/v1/open-close/AAPL/2024-11-06?adjusted=true&apiKey=NbKXkuoH3mdV4H6DN493zVQg0M2d0LlG"
                    };
            /* API Endpoint Format: https://api.polygon.io/v1/open-close/COMPANY_CODE/DATE?adjusted=true&apiKey=NbKXkuoH3mdV4H6DN493zVQg0M2d0LlG
            * COMPANY_CODE: String
            * DATE: YYYY-MM-DD
            * */

            String apiEndpoint = listOfApi[1];

            // Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create the request for the provided API endpoint
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiEndpoint))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response body (String Text)
            System.out.println(response.body());

            /* Data Provided for a certain date:
            * 1. Date: YYYY-MM-DD [Format]
            * 2. Symbol (Company Code): String; Example = AAPL [Apple]
            * 3. Opening Stock (Corresponding Date): Double
            * 4. Highest Stock (Corresponding Date): Double
            * 5. Lowest Stock (Corresponding Date): Double
            * 6. Close Stock (Corresponding Date): Double
            * 7. Volume (Corresponding Date): Double
            * */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
