/*
package test.entities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import entities.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.MockMetrics;
import test.MockStockDataLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StockTest {
    private MockStockDataLoader dataLoader;
    private Stock stock;
    private String company = "Apple";
    private String symbol = "AAPL";

    @BeforeEach
    void setUp() {
        dataLoader = new MockStockDataLoader();
        company = "Apple";
        symbol = "AAPL";
        stock = new Stock(dataLoader, company, symbol);
    }

    @Test
    void getCompany() {
        final String expectedValue = company;
        assertEquals(stock.getCompany(), expectedValue);
    }

    @Test
    void getSymbol() {
        final String expectedValue = symbol;
        assertEquals(stock.getSymbol(), expectedValue);
    }

    @Test
    void getSharePrice() {
        final Date testDate = MockMetrics.makeDates().getFirst();
        final Double expectedValue = dataLoader.getSharePrices(symbol,
                testDate, toDate(toLocalDate(testDate).plusDays(1))).getValue(testDate);
        assertEquals(stock.getSharePrice(testDate), expectedValue);
    }

    @Test
    void getVolume() {
        final Date testDate = MockMetrics.makeDates().getFirst();
        final Double expectedValue = dataLoader.getVolumes(symbol,
                testDate, toDate(toLocalDate(testDate).plusDays(1))).getValue(testDate);
        assertEquals(stock.getVolume(testDate), expectedValue);
    }

    @Test
    void getGrowthPercentage() {
        final Date start = MockMetrics.makeDates().getFirst();
        final Date end = MockMetrics.makeDates().getLast();
        final Double startPrice = dataLoader.getSharePrices(symbol, start, end).getValue(start);
        final Double endPrice = dataLoader.getSharePrices(symbol, start, end).getValue(end);

        final Double expectedValue = (endPrice - startPrice) / startPrice * 100;
        assertEquals(stock.getGrowthPercentage(start, end), expectedValue);
    }

    */
/*@Test
    void getEarningsPerShare() {
        final Date start = MockMetrics.makeDates().get(0);
        final Date end = MockMetrics.makeDates().get(1);
        final Double totalEarnings = dataLoader.getSharePrices(symbol, start, end).getValue(start)
                        + dataLoader.getSharePrices(symbol, start, end).getValue(end);
        assertEquals(stock.getEarningsPerShare(start, end),
                totalEarnings / dataLoader.getSharePrices(symbol, start, end).getLatest());
    }*//*


    */
/*@Test
    void getDividendsPerShare() {
        final Date testDate = MockMetrics.makeDates().getFirst();
        final Double expectedValue = dataLoader.getDividends().getValue(testDate);
        assertEquals(stock.getDividendsPerShare(testDate), expectedValue);
    }*//*


    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // Convert Date to LocalDate
    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Test
    void favoritingFeature() {
        stock.setFavorite(true);
        assertTrue(stock.isFavorite());
    }
}
}*/
