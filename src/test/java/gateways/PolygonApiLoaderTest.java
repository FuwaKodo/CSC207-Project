package gateways;

import entities.MetricValues;
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
    public void getVolume() {
        Calendar calendar = Calendar.getInstance();
        Date day = polygonApiLoaderExample.setDay(calendar, 2024, 10, 6);
        Double result = polygonApiLoaderExample.getVolume("INTC", day);
        Double expected = 1.14559359e+08;
        assertEquals(expected, result);
    }

    @Test
    public void getVolumeHandlesInvalidDate() {
        Calendar calendar = Calendar.getInstance();
        Date day = polygonApiLoaderExample.setDay(calendar, Integer.MAX_VALUE, 10, 6);
        Double result = polygonApiLoaderExample.getVolume("INTC", day);
        Double expected = Double.NaN;
        assertEquals(expected, result);
    }

    @Test
    public void getVolumeHandlesInvalidSymbol() {
        Calendar calendar = Calendar.getInstance();
        Date day = polygonApiLoaderExample.setDay(calendar, 2024, 10, 6);
        Double result = polygonApiLoaderExample.getVolume("", day);
        Double expected = Double.NaN;
        assertEquals(expected, result);
    }

    @Test
    public void getVolumesForOneDay() {
        Calendar calendar = Calendar.getInstance();
        Date day = polygonApiLoaderExample.setDay(calendar, 2024, 10, 6);
        ArrayList<Double> expectedValues = new ArrayList<>();
        expectedValues.add(1.14559359e+08);
        ArrayList<Date> expectedDates = new ArrayList<>();
        expectedDates.add(day);
        MetricValues expected = new MetricValues(expectedValues, expectedDates);
        MetricValues actual = polygonApiLoaderExample.getVolumes("INTC", day, day);
        assertEquals(expected, actual);
    }

    @Test
    public void getVolumesForWeekDays(){
        Calendar calendar = Calendar.getInstance();
        Date startDay = polygonApiLoaderExample.setDay(calendar, 2024, 10, 4);
        Date endDay = polygonApiLoaderExample.setDay(calendar, 2024, 10, 8);

        ArrayList<Double> expectedValues = new ArrayList<>();
        Double volume4Nov2024 = 8.387316e+07;
        Double volume5Nov2024 = 5.846998e+07;
        Double volume6Nov2024 = 1.14559359e+08;
        Double volume7Nov2024 = 9.8603816e+07;
        Double volume8Nov2024 = 7.5376131e+07;

        expectedValues.add(volume4Nov2024);
        expectedValues.add(volume5Nov2024);
        expectedValues.add(volume6Nov2024);
        expectedValues.add(volume7Nov2024);
        expectedValues.add(volume8Nov2024);

        ArrayList<Date> expectedDates = new ArrayList<>();
        expectedDates.add(startDay);
        polygonApiLoaderExample.setDay(calendar, 2024, 10, 4);

        Date secondDay = polygonApiLoaderExample.addDay(calendar, 1);
        expectedDates.add(secondDay);

        Date thirdDay = polygonApiLoaderExample.addDay(calendar, 1);
        expectedDates.add(thirdDay);

        Date fourthDay = polygonApiLoaderExample.addDay(calendar, 1);
        expectedDates.add(fourthDay);

        expectedDates.add(endDay);

        MetricValues expected = new MetricValues(expectedValues, expectedDates);
        MetricValues actual = polygonApiLoaderExample.getVolumes("INTC", startDay, endDay);
        assertEquals(expected, actual);
    }

    @Test
    public void getVolumesForEntireWeek(){
        Calendar calendar = Calendar.getInstance();
        Date startDay = polygonApiLoaderExample.setDay(calendar, 2024, 10, 3);
        Date endDay = polygonApiLoaderExample.setDay(calendar, 2024, 10, 9);

        ArrayList<Double> expectedValues = new ArrayList<>();
        Double volume3Nov2024 = Double.NaN;
        Double volume4Nov2024 = 8.387316e+07;
        Double volume5Nov2024 = 5.846998e+07;
        Double volume6Nov2024 = 1.14559359e+08;
        Double volume7Nov2024 = 9.8603816e+07;
        Double volume8Nov2024 = 7.5376131e+07;
        Double volume9Nov2024 = Double.NaN;

        expectedValues.add(volume3Nov2024);
        expectedValues.add(volume4Nov2024);
        expectedValues.add(volume5Nov2024);
        expectedValues.add(volume6Nov2024);
        expectedValues.add(volume7Nov2024);
        expectedValues.add(volume8Nov2024);
        expectedValues.add(volume9Nov2024);

        ArrayList<Date> expectedDates = new ArrayList<>();
        expectedDates.add(startDay);
        polygonApiLoaderExample.setDay(calendar, 2024, 10, 3);

        Date secondDay = polygonApiLoaderExample.addDay(calendar, 1);
        expectedDates.add(secondDay);

        Date thirdDay = polygonApiLoaderExample.addDay(calendar, 1);
        expectedDates.add(thirdDay);

        Date fourthDay = polygonApiLoaderExample.addDay(calendar, 1);
        expectedDates.add(fourthDay);

        Date fifthDay = polygonApiLoaderExample.addDay(calendar, 1);
        expectedDates.add(fifthDay);

        Date sixthDay = polygonApiLoaderExample.addDay(calendar, 1);
        expectedDates.add(sixthDay);

        expectedDates.add(endDay);

        MetricValues expected = new MetricValues(expectedValues, expectedDates);
        MetricValues actual = polygonApiLoaderExample.getVolumes("INTC", startDay, endDay);

        System.out.println(expectedDates.toString());
        System.out.println(expectedValues.toString());
        assertEquals(expected, actual);
    }
}
