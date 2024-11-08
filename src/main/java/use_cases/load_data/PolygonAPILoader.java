package use_cases.load_data;

import java.util.List;

// API Loader
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Java Date
import java.time.LocalDate;

public class PolygonAPILoader implements StockDataLoader {
    private String endpointFormatAPI = "https://api.polygon.io/v1/open-close/COMPANY_CODE/DATE?" +
            "adjusted=true&apiKey=NbKXkuoH3mdV4H6DN493zVQg0M2d0LlG";

    // Load for previous year




    @Override
    public String getCompany() {
        return "";
    }

    @Override
    public String getSymbol() {
        return "";
    }

    @Override
    public List<Double> getSharePrices() {
        return List.of();
    }

    @Override
    public List<Double> getEarnings() {
        return List.of();
    }

    public static void main(String[] args){
        System.out.println("Hello World");
        System.out.println("It's crazy");
    }
}


