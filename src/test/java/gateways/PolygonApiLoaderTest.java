package gateways;

import entities.MetricValues;
import interface_adapters.gateways.PolygonApiLoader;
import org.json.JSONObject;
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
    public void clearTimeFields() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 6);
        polygonApiLoaderExample.clearTimeFields(calendar);
        int resultHour = calendar.get(Calendar.HOUR_OF_DAY);
        int resultMinute = calendar.get(Calendar.MINUTE);
        int resultSecond = calendar.get(Calendar.SECOND);
        int[] resultValues = {resultHour, resultMinute, resultSecond};
        int[] expectedValues = {0, 0, 0};
        assertArrayEquals(expectedValues, resultValues);
    }

    @Test
    public void setDay() {
        Calendar calendar = Calendar.getInstance();
        Date resultDate = polygonApiLoaderExample.setDay(calendar, 2024, 10, 6);
        calendar.set(2024, Calendar.NOVEMBER, 6);
        polygonApiLoaderExample.clearTimeFields(calendar);
        Date expectedDate = calendar.getTime();
        assertEquals(expectedDate, resultDate);
    }

    @Test
    public void addDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 6);
        Date resultDate = polygonApiLoaderExample.addDay(calendar, 1);
        calendar.set(2024, Calendar.NOVEMBER, 7);
        polygonApiLoaderExample.clearTimeFields(calendar);
        Date expectedDate = calendar.getTime();
        assertEquals(expectedDate, resultDate);
    }

    @Test
    public void loadOneEntry(){
        Calendar calendar = Calendar.getInstance();
        Date startDay = polygonApiLoaderExample.setDay(calendar, 2024, 10, 6);
        JSONObject resultJson = polygonApiLoaderExample.loadOneEntry("INTC", startDay);
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("status", "OK");
        expectedJson.put("from", "2024-11-06");
        expectedJson.put("symbol", "INTC");
        expectedJson.put("open", 24.3);
        expectedJson.put("high", 25.12);
        expectedJson.put("low", 24.05);
        expectedJson.put("close", 25.05);
        expectedJson.put("volume", 1.14559359e+08);
        expectedJson.put("afterHours", 25.03);
        expectedJson.put("preMarket", 24.08);
        System.out.println(expectedJson.getDouble("volume"));
        System.out.println(resultJson.getDouble("volume"));
    }
}
