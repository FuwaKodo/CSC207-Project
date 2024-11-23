package gateways;

import interface_adapters.gateways.PolygonApiLoader;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class PolygonApiLoaderTest {
    private PolygonApiLoader polygonApiLoaderExample = new PolygonApiLoader();

    //TODO: Ask because the test is for interface should I make a test for interface?
    @Test
    public void buildApiUrl() {
        String baseUrl = "https://api.polygon.io/v1/";
        ArrayList<String> endpoint = new ArrayList<>();
        endpoint.add("open-close");
        endpoint.add("INTC");
        endpoint.add("2024-11-06");
        TreeMap<String, String> queryParameters = new TreeMap<>();
        queryParameters.put("adjusted", "true");
        queryParameters.put("apiKey", "NbKXkuoH3mdV4H6DN493zVQg0M2d0LlG");
        String result = polygonApiLoaderExample.buildApiUrl(baseUrl, endpoint, queryParameters);
        String expected = "https://api.polygon.io/v1/open-close/INTC/2024-11-06?adjusted=true&apiKey=NbKXkuoH3mdV4H6DN493zVQg0M2d0LlG";
        assertEquals(expected, result);
    }

    @Test
    public void buildApiUrlHandlesEmptyQuery() {
        String baseUrl = "https://api.polygon.io/v1/";
        ArrayList<String> endpoint = new ArrayList<>();
        endpoint.add("open-close");
        endpoint.add("INTC");
        endpoint.add("2024-11-06");
        TreeMap<String, String> queryParameters = new TreeMap<>();
        String result = polygonApiLoaderExample.buildApiUrl(baseUrl, endpoint, queryParameters);
        String expected = "https://api.polygon.io/v1/open-close/INTC/2024-11-06";
        assertEquals(expected, result);
    }

    @Test
    public void buildApiUrlHandlesEmptyEndpoint() {
        String baseUrl = "https://api.polygon.io/v1/";
        ArrayList<String> endpoint = new ArrayList<>();
        TreeMap<String, String> queryParameters = new TreeMap<>();
        queryParameters.put("adjusted", "true");
        queryParameters.put("apiKey", "NbKXkuoH3mdV4H6DN493zVQg0M2d0LlG");

        String result = polygonApiLoaderExample.buildApiUrl(baseUrl, endpoint, queryParameters);
        String expected = "https://api.polygon.io/v1/?adjusted=true&apiKey=NbKXkuoH3mdV4H6DN493zVQg0M2d0LlG";
        assertEquals(expected, result);
    }

    @Test
    public void buildApiUrlHandlesEmptyQueryAndEmptyEndpoint() {
        String baseUrl = "https://api.polygon.io/v1/";
        ArrayList<String> endpoint = new ArrayList<>();
        TreeMap<String, String> queryParameters = new TreeMap<>();

        String result = polygonApiLoaderExample.buildApiUrl(baseUrl, endpoint, queryParameters);
        String expected = "https://api.polygon.io/v1/";
        assertEquals(expected, result);
    }

    @Test
    public void getVolume() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 6);
        Date day = calendar.getTime();
        Double result = polygonApiLoaderExample.getVolume("INTC", day);
        Double expected = 1.14559359e+08;
        assertEquals(expected, result);
    }

    @Test
    public void getVolumeHandlesInvalidDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 40);
        Date day = calendar.getTime();
        Double result = polygonApiLoaderExample.getVolume("INTC", day);
        Double expected = Double.NaN;
        assertEquals(expected, result);
    }

    @Test
    public void getVolumeHandlesInvalidSymbol() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 6);
        Date day = calendar.getTime();
        Double result = polygonApiLoaderExample.getVolume("", day);
        Double expected = Double.NaN;
        assertEquals(expected, result);
    }
}
