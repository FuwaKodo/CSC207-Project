package frameworks;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class StockDataLoaderTest {
    private StockDataLoader stockDataLoaderExample = new StockDataLoader();

    /**
     * Sets the given Calendar object to the specified day and resets the time to midnight (00:00:00.000).
     * @param calendar the Calendar object to modify. Must not be null.
     * @param year the year to set (e.g., 2024).
     * @param month the month to set (0-based, e.g., 0 for January, 11 for December).
     * @param day the day of the month to set (1-based, e.g., 1 for the first day of the month).
     * @return Date object representing the given dates on midnight
     */
    private Date setDay(Calendar calendar, int year, int month, int day) {
        calendar.set(year, month, day);
        stockDataLoaderExample.clearTimeFields(calendar);
        return calendar.getTime();
    }

    @Test
    public void checkStockDataJSON() {
        assertEquals(true,  stockDataLoaderExample.checkStockDataJSON());
    }

    @Test
    public void createStockDataJSON(){
        assertEquals(false,  stockDataLoaderExample.createStockDataJSON());
    }

    @Test
    public void getVolume(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 10,6);
        Date date = calendar.getTime();
        System.out.println(stockDataLoaderExample.getVolume("INTC", date));
    }

    @Test
    public void getVolumeForMissingData(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 10,12);
        Date date = calendar.getTime();
        System.out.println(stockDataLoaderExample.getVolume("INTC", date));
    }

    @Test
    public void updateStockData(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 10,7);
        Date date = calendar.getTime();
        stockDataLoaderExample.updateStockData("INTC", date);
    }

    @Test
    public void updateStockDataInvalidDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 10,9);
        Date date = calendar.getTime();
        stockDataLoaderExample.updateStockData("INTC", date);
    }

    @Test
    public void getVolumes(){
        Calendar calendar = Calendar.getInstance();
        Date startDate = setDay(calendar, 2024, 10, 6);
        Date endDate = setDay(calendar, 2024, 10, 12);
        stockDataLoaderExample.getVolumes("INTC", startDate, endDate);
    }

    @Test
    public void getAfterHour(){
        Calendar calendar = Calendar.getInstance();
        Date date = setDay(calendar, 2024, 10, 6);
        System.out.println(stockDataLoaderExample.getAfterHour("INTC", date));
    }

    @Test
    public void getAfterHours(){
        Calendar calendar = Calendar.getInstance();
        Date startDate = setDay(calendar, 2024, 10, 6);
        Date endDate = setDay(calendar, 2024, 10, 12);
        stockDataLoaderExample.getAfterHours("INTC", startDate, endDate);
    }
}
