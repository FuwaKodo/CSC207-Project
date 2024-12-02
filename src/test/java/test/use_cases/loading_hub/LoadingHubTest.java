package test.use_cases.loading_hub;

import entities.MetricValues;
import entities.SharePrices;
import interface_adapters.gateways.StockDataLoader;
import interface_adapters.gateways.StockSymbolsLoader;
import org.junit.Test;
import use_cases.StockDataInterface;
import use_cases.SymbolNameDataAccessInterface;
import use_cases.loading_hub.LoadingHubInputBoundary;
import use_cases.loading_hub.LoadingHubInputData;
import use_cases.loading_hub.LoadingHubInteractor;
import use_cases.loading_hub.LoadingHubOutputBoundary;
import use_cases.loading_hub.LoadingHubOutputData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoadingHubTest {
    final private StockDataInterface dataAccessObject = new StockDataLoader();
    final private SymbolNameDataAccessInterface symbolNameDataAccessInterface = new StockSymbolsLoader();

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
        clearTimeFields(calendar);
        return calendar.getTime();
    }

    /**
     * Clears the time fields of the given Calendar object, setting the time to midnight (00:00:00.000).
     * @param calendar the Calendar object to modify. Must not be null.
     */
    public void clearTimeFields(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    @Test
    public void executeOneTest() {
        LoadingHubOutputBoundary loadingHubPresenter = new LoadingHubOutputBoundary() {
            @Override
            public void displayResult(LoadingHubOutputData outputData) {
                Calendar calendar = Calendar.getInstance();

                List<Date> dates = new ArrayList<>();
                Date currentDate = setDay(calendar, 2024, 10,6);
                dates.add(currentDate);
                List<Double> openPrices = new ArrayList<>();
                openPrices.add(24.3);
                List<Double> closePrices = new ArrayList<>();
                closePrices.add(25.05);
                List<Double> highPrices = new ArrayList<>();
                highPrices.add(25.12);
                List<Double> lowPrices = new ArrayList<>();
                lowPrices.add(24.05);

                List<Double> volumes = new ArrayList<>();
                volumes.add(1.14559359e08);

                List<Double> afterHours = new ArrayList<>();
                afterHours.add(25.03);

                List<Double> preMarkets = new ArrayList<>();
                preMarkets.add(24.08);

                SharePrices sharePrice = new SharePrices(dates, openPrices, closePrices, highPrices, lowPrices);
                MetricValues volume = new MetricValues(volumes, dates);
                MetricValues afterHour = new MetricValues(afterHours, dates);
                MetricValues preMarket = new MetricValues(preMarkets, dates);

                assert(outputData.getStockSymbol().equals("INTC"));
                assert(outputData.getCompanyName().equals("Intel"));
                assert(outputData.getSharePrices().equals(sharePrice));
                assert(outputData.getVolumes().equals(volume));
                assert(outputData.getAfterHours().equals(afterHour));
                assert(outputData.getPreMarket().equals(preMarket));
            }
        };

        Calendar myCalendar = Calendar.getInstance();
        Date startDate = setDay(myCalendar, 2024, 10, 6);
        Date endDate = setDay(myCalendar, 2024, 10, 6);
        LoadingHubInputData inputData = new LoadingHubInputData("INTC", startDate, endDate);
        LoadingHubInputBoundary loadingHubInteractor = new LoadingHubInteractor(loadingHubPresenter, dataAccessObject,
                symbolNameDataAccessInterface);

        loadingHubInteractor.execute(inputData);
    }

    @Test
    public void executeMissingInfo() {
        LoadingHubOutputBoundary loadingHubPresenter = new LoadingHubOutputBoundary() {
            @Override
            public void displayResult(LoadingHubOutputData outputData) {
                Calendar calendar = Calendar.getInstance();
                Date currDay = setDay(calendar, 2020, 0, 1);

                List<Date> dates = new ArrayList<>();
                dates.add(currDay);

                List<Double> openPrices = new ArrayList<>();
                List<Double> closePrices = new ArrayList<>();
                List<Double> highPrices = new ArrayList<>();
                List<Double> lowPrices = new ArrayList<>();

                List<Double> volumes = new ArrayList<>();
                volumes.add(Double.NaN);

                List<Double> afterHours = new ArrayList<>();
                afterHours.add(Double.NaN);

                List<Double> preMarkets = new ArrayList<>();
                preMarkets.add(Double.NaN);

                SharePrices sharePrice = new SharePrices(dates, openPrices, closePrices, highPrices, lowPrices);
                MetricValues volume = new MetricValues(volumes, dates);
                MetricValues afterHour = new MetricValues(afterHours, dates);
                MetricValues preMarket = new MetricValues(preMarkets, dates);

                assert(outputData.getStockSymbol().equals("AAPL"));
                assert(outputData.getCompanyName().equals("Apple"));
                assert(outputData.getSharePrices().equals(sharePrice));
                assert(outputData.getVolumes().equals(volume));
                assert(outputData.getAfterHours().equals(afterHour));
                assert(outputData.getPreMarket().equals(preMarket));
            }
        };

        Calendar myCalendar = Calendar.getInstance();
        Date startDate = setDay(myCalendar, 2020, 0, 1);
        Date endDate = setDay(myCalendar, 2020, 0, 1);
        LoadingHubInputData inputData = new LoadingHubInputData("AAPL", startDate, endDate);
        LoadingHubInputBoundary loadingHubInteractor = new LoadingHubInteractor(loadingHubPresenter, dataAccessObject,
                symbolNameDataAccessInterface);

        loadingHubInteractor.execute(inputData);
    }

    @Test
    public void executeMultipleDays() {
        LoadingHubOutputBoundary loadingHubPresenter = new LoadingHubOutputBoundary() {
            @Override
            public void displayResult(LoadingHubOutputData outputData) {
                Calendar calendar = Calendar.getInstance();

                List<Date> dates = new ArrayList<>();
                Date startDate = setDay(calendar, 2024, 10,6);
                Date endDate = setDay(calendar, 2024, 10, 7);
                dates.add(startDate);
                dates.add(endDate);
                List<Double> openPrices = new ArrayList<>();
                openPrices.add(24.3);
                openPrices.add(25.43);
                List<Double> closePrices = new ArrayList<>();
                closePrices.add(25.05);
                closePrices.add(26.23);
                List<Double> highPrices = new ArrayList<>();
                highPrices.add(25.12);
                highPrices.add(26.3799);
                List<Double> lowPrices = new ArrayList<>();
                lowPrices.add(24.05);
                lowPrices.add(25.19);

                List<Double> volumes = new ArrayList<>();
                volumes.add(1.14559359e08);
                volumes.add(9.8603816e07);

                List<Double> afterHours = new ArrayList<>();
                afterHours.add(25.03);
                afterHours.add(26.34);

                List<Double> preMarkets = new ArrayList<>();
                preMarkets.add(24.08);
                preMarkets.add(25.07);

                SharePrices sharePrice = new SharePrices(dates, openPrices, closePrices, highPrices, lowPrices);
                MetricValues volume = new MetricValues(volumes, dates);
                MetricValues afterHour = new MetricValues(afterHours, dates);
                MetricValues preMarket = new MetricValues(preMarkets, dates);

                assert(outputData.getStockSymbol().equals("INTC"));
                assert(outputData.getCompanyName().equals("Intel"));
                assert(outputData.getSharePrices().equals(sharePrice));
                assert(outputData.getVolumes().equals(volume));
                assert(outputData.getAfterHours().equals(afterHour));
                assert(outputData.getPreMarket().equals(preMarket));
            }
        };

        Calendar myCalendar = Calendar.getInstance();
        Date startDate = setDay(myCalendar, 2024, 10, 6);
        Date endDate = setDay(myCalendar, 2024, 10, 7);
        LoadingHubInputData inputData = new LoadingHubInputData("INTC", startDate, endDate);
        LoadingHubInputBoundary loadingHubInteractor = new LoadingHubInteractor(loadingHubPresenter, dataAccessObject,
                symbolNameDataAccessInterface);

        loadingHubInteractor.execute(inputData);
    }

    @Test
    public void executeMultipleDaysAndMissingInfo() {
        LoadingHubOutputBoundary loadingHubPresenter = new LoadingHubOutputBoundary() {
            @Override
            public void displayResult(LoadingHubOutputData outputData) {
                Calendar calendar = Calendar.getInstance();

                List<Date> dates = new ArrayList<>();
                Date startDate = setDay(calendar, 2024, 10,6);
                Date secondDate = setDay(calendar, 2024, 10, 7);
                Date thirdDate = setDay(calendar, 2024, 10, 8);
                Date fourthDate = setDay(calendar, 2024, 10, 9);

                dates.add(startDate);
                dates.add(secondDate);
                dates.add(thirdDate);
                dates.add(fourthDate);

                List<Double> openPrices = new ArrayList<>();
                openPrices.add(24.3);
                openPrices.add(25.43);
                openPrices.add(26d);

                List<Double> closePrices = new ArrayList<>();
                closePrices.add(25.05);
                closePrices.add(26.23);
                closePrices.add(26.2);

                List<Double> highPrices = new ArrayList<>();
                highPrices.add(25.12);
                highPrices.add(26.3799);
                highPrices.add(26.43);

                List<Double> lowPrices = new ArrayList<>();
                lowPrices.add(24.05);
                lowPrices.add(25.19);
                lowPrices.add(25.83);

                List<Double> volumes = new ArrayList<>();
                volumes.add(1.14559359e08);
                volumes.add(9.8603816e07);
                volumes.add(7.5376131e07);
                volumes.add(Double.NaN);

                List<Double> afterHours = new ArrayList<>();
                afterHours.add(25.03);
                afterHours.add(26.34);
                afterHours.add(26.1);
                afterHours.add(Double.NaN);

                List<Double> preMarkets = new ArrayList<>();
                preMarkets.add(24.08);
                preMarkets.add(25.07);
                preMarkets.add(26.44);
                preMarkets.add(Double.NaN);

                SharePrices sharePrice = new SharePrices(dates, openPrices, closePrices, highPrices, lowPrices);

                assert(outputData.getStockSymbol().equals("INTC"));
                assert(outputData.getCompanyName().equals("Intel"));

                assert(outputData.getSharePrices().getHighPrices().equals(highPrices));
                assert(outputData.getSharePrices().getLowPrices().equals(lowPrices));

                // Doesn't run
                assert(outputData.getSharePrices().getOpenPrices().equals(openPrices));
                System.out.println("hello");

                assert(outputData.getSharePrices().equals(sharePrice));

                List<Date> newDate = new ArrayList<>();
                newDate.add(startDate);
                newDate.add(secondDate);
                newDate.add(thirdDate);
                // Date fourthDate = setDay(calendar, 2024, 10, 9);
                newDate.add(fourthDate);

                MetricValues volume = new MetricValues(volumes, newDate);
                MetricValues afterHour = new MetricValues(afterHours, newDate);
                MetricValues preMarket = new MetricValues(preMarkets, newDate);

                assert(outputData.getVolumes().equals(volume));
                assert(outputData.getAfterHours().equals(afterHour));
                assert(outputData.getPreMarket().equals(preMarket));
            }
        };

        Calendar myCalendar = Calendar.getInstance();
        Date startDate = setDay(myCalendar, 2024, 10, 6);
        Date endDate = setDay(myCalendar, 2024, 10, 9);
        LoadingHubInputData inputData = new LoadingHubInputData("INTC", startDate, endDate);
        LoadingHubInputBoundary loadingHubInteractor = new LoadingHubInteractor(loadingHubPresenter, dataAccessObject,
                symbolNameDataAccessInterface);

        loadingHubInteractor.execute(inputData);
    }
}
