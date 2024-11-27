package test;

import entities.MetricValues;
import entities.Metrics;
import entities.SharePrices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

class MetricsTest {
    private Metrics metrics;
    private final List<Double> mockRawValues = MockMetrics.makeRawValues();
    private final List<LocalDate> mockDates = MockMetrics.makeDates();

    @BeforeEach
    void setUp() {
        final SharePrices sharePrices = MockMetrics.makeSharePrices();
        final MetricValues otherValues = MockMetrics.makeMetricValues();
        metrics = new Metrics(sharePrices, otherValues, otherValues, otherValues);
    }

    @Test
    void getSharePrice() {
        final LocalDate date = mockDates.get(0);
        final Double expectedPrice = mockRawValues.get(0);
        assertEquals(metrics.getSharePrice(date), expectedPrice);
    }

    @Test
    void getVolume() {
        final LocalDate date = mockDates.get(0);
        final Double expectedVolume = mockRawValues.get(0);
        assertEquals(metrics.getVolume(date), expectedVolume);
    }

    @Test
    void getGrowthPercentage() {
        final LocalDate start = mockDates.get(0);
        final LocalDate end = mockDates.get(2);
        final Double startPrice = metrics.getSharePrice(start);
        final Double endPrice = metrics.getSharePrice(end);
        final Double expectedValue = (endPrice - startPrice) / startPrice * 100;
        assertEquals(metrics.getGrowthPercentage(start, end), expectedValue);
    }

    @Test
    void getEarningsPerShare() {
        final LocalDate start = mockDates.get(0);
        final LocalDate end = mockDates.get(1);
        final Double totalEarnings = mockRawValues.get(0) + mockRawValues.get(1);
        assertEquals(metrics.getEarningsPerShare(start, end),
                totalEarnings / metrics.getSharePrice(mockDates.get(2)));
    }

    @Test
    void getDividendsPerShare() {
        final LocalDate date = mockDates.get(0);
        final Double expectedValue = mockRawValues.get(0);
        assertEquals(metrics.getDividendsPerShare(date), expectedValue);
    }

    @Test
    void getNonexistentMetricValue() {
        final int lastIndex = mockDates.size() - 1;
        final LocalDate latest = mockDates.get(lastIndex);
        final LocalDate nonexistentDate = latest.plusDays(1);

        final Double expectedValue = metrics.getSharePrice(latest);
        assertEquals(expectedValue, metrics.getSharePrice(nonexistentDate));
    }
}