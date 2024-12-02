package test.use_cases.view_stock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entities.MetricValues;
import entities.SharePrices;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import use_cases.StockDataInterface;
import use_cases.SymbolNameDataAccessInterface;
import use_cases.view_stock.ViewStockInputBoundary;
import use_cases.view_stock.ViewStockInputData;
import use_cases.view_stock.ViewStockInteractor;
import use_cases.view_stock.ViewStockOutputBoundary;
import use_cases.view_stock.ViewStockOutputData;

public class ViewStockInteractorTest {
    private String symbol = "AAPL";
    private List<Date> dates = new ArrayList<Date>();
    private final List<Double> openPrices = new ArrayList<>(List.of(10.0, 12.0, 11.0));
    private final List<Double> closePrices = new ArrayList<>(List.of(11.0, 12.0, 10.0));
    private final List<Double> highPrices = new ArrayList<>(List.of(12.0, 14.0, 11.0));
    private final List<Double> lowPrices = new ArrayList<>(List.of(9.0, 11.0, 8.0));
    private final StockDataInterface dataAccessObject = new StockDataInterface() {
        @Override
        public Double getVolume(String stockSymbol, Date date) {
            return 0.0;
        }

        @Override
        public MetricValues getVolumes(String stockSymbol, Date startDate, Date endDate) {
            return null;
        }

        @Override
        public Double getAfterHour(String stockSymbol, Date date) {
            return 0.0;
        }

        @Override
        public MetricValues getAfterHours(String stockSymbol, Date startDate, Date endDate) {
            return null;
        }

        @Override
        public Double getPremarket(String stockSymbol, Date date) {
            return 0.0;
        }

        @Override
        public MetricValues getPremarkets(String stockSymbol, Date startDate, Date endDate) {
            return null;
        }

        @Override
        public SharePrices getSharePrice(String stockSymbol, Date date) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            final Date tmr = calendar.getTime();
            return newSharePrices(date, tmr);
        }

        @Override
        public SharePrices getSharePrices(String stockSymbol, Date startDate, Date endDate) {
            return newSharePrices(startDate, endDate);
        }
    };
    private final SymbolNameDataAccessInterface symbolDAO = new SymbolNameDataAccessInterface() {
        @Override
        public List<String> getSymbols() {
            return List.of();
        }

        @Override
        public String getCompany(String symbol) {
            return "Apple";
        }

        @Override
        public String getSymbol(String company) {
            return "AAPL";
        }
    };

    private SharePrices newSharePrices(Date startDate, Date endDate) {
        final int startIndex = dates.indexOf(startDate);
        final int endIndex = dates.indexOf(endDate);
        return new SharePrices(dates.subList(startIndex, endIndex),
                openPrices.subList(startIndex, endIndex),
                closePrices.subList(startIndex, endIndex),
                highPrices.subList(startIndex, endIndex),
                lowPrices.subList(startIndex, endIndex));
    }

    @Test
    public void executeTest() {
        // Calculate today's date
        final Date today = new Date();
        // Calculate 15 days before today
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        final Date yesterday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        final Date yesyesterday = calendar.getTime();

        dates.add(yesyesterday);
        dates.add(yesterday);
        dates.add(today);

        ViewStockOutputBoundary viewStockPresenter = new ViewStockOutputBoundary() {
            @Override
            public void displayStock(ViewStockOutputData viewStockOutputData) {
                assert(viewStockOutputData.getCompany().equals("Apple"));
                assert(viewStockOutputData.getSymbol().equals(symbol));
                assert(viewStockOutputData.getSharePrices()
                        .equals(newSharePrices(dates.getFirst(), dates.getLast())));
            }
        };
        ViewStockInputData inputData = new ViewStockInputData(symbol, dates.getFirst(), dates.getLast());
        ViewStockInputBoundary viewStockInteractor =
                new ViewStockInteractor(viewStockPresenter, dataAccessObject, symbolDAO);

        viewStockInteractor.execute(inputData);
    }
}
