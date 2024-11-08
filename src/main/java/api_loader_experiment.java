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

            String apiEndpoint = listOfApi[1];

            // Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create the request for the provided API endpoint
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiEndpoint))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response body (usually JSON data)
            System.out.println(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
