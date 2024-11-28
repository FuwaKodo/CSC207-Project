package test;

import entities.Stock;
import interface_adapters.gateways.StockDataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {
    private StockDataLoader dataLoader;
    private Stock stock;

    @BeforeEach
    void setUp() {
        dataLoader = new MockStockDataLoader();
        stock = new Stock(dataLoader);
    }

    @Test
    void getCompany() {
        final String expectedValue = dataLoader.getCompany();
        assertEquals(stock.getCompany(), expectedValue);
    }

    @Test
    void getSymbol() {
        final String expectedValue = dataLoader.getSymbol();
        assertEquals(stock.getSymbol(), expectedValue);
    }

    @Test
    void getSharePrice() {
        final LocalDate testDate = MockMetrics.makeDates().getFirst();
        final Double expectedValue = dataLoader.getSharePrices().getValue(testDate);
        assertEquals(stock.getSharePrice(testDate), expectedValue);
    }

    @Test
    void getVolume() {
        final LocalDate testDate = MockMetrics.makeDates().getFirst();
        final Double expectedValue = dataLoader.getVolumes().getValue(testDate);
        assertEquals(stock.getVolume(testDate), expectedValue);
    }

    @Test
    void getGrowthPercentage() {
        final LocalDate start = MockMetrics.makeDates().getFirst();
        final LocalDate end = MockMetrics.makeDates().getLast();
        final Double startPrice = dataLoader.getSharePrices().getValue(start);
        final Double endPrice = dataLoader.getSharePrices().getValue(end);

        final Double expectedValue = (endPrice - startPrice) / startPrice * 100;
        assertEquals(stock.getGrowthPercentage(start, end), expectedValue);
    }

    @Test
    void getEarningsPerShare() {
        final LocalDate start = MockMetrics.makeDates().get(0);
        final LocalDate end = MockMetrics.makeDates().get(1);
        final Double totalEarnings =
                dataLoader.getSharePrices().getValue(start) + dataLoader.getSharePrices().getValue(end);
        assertEquals(stock.getEarningsPerShare(start, end),
                totalEarnings / dataLoader.getSharePrices().getLatest());
    }

    @Test
    void getDividendsPerShare() {
        final LocalDate testDate = MockMetrics.makeDates().getFirst();
        final Double expectedValue = dataLoader.getDividends().getValue(testDate);
        assertEquals(stock.getDividendsPerShare(testDate), expectedValue);
    }
}