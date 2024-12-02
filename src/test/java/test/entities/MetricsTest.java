package test.entities;

import entities.MetricValues;
import entities.Metrics;
import entities.SharePrices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.MockMetrics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class MetricsTest {
    private Metrics metrics;
    private final List<Double> mockRawValues = MockMetrics.makeRawValues();
    private final List<Date> mockDates = MockMetrics.makeDates();

    @BeforeEach
    void setUp() {
        final SharePrices sharePrices = MockMetrics.makeSharePrices();
        final MetricValues otherValues = MockMetrics.makeMetricValues();
        metrics = new Metrics(sharePrices, otherValues, otherValues, otherValues);
    }

    @Test
    void getSharePrice() {
        final Date date = mockDates.get(0);
        final Double expectedPrice = mockRawValues.get(0);
        assertEquals(metrics.getSharePrice(date), expectedPrice);
    }

    @Test
    void getVolume() {
        final Date date = mockDates.get(0);
        final Double expectedVolume = mockRawValues.get(0);
        assertEquals(metrics.getVolume(date), expectedVolume);
    }

    @Test
    void getGrowthPercentage() {
        final Date start = mockDates.get(0);
        final Date end = mockDates.get(2);
        final Double startPrice = metrics.getSharePrice(start);
        final Double endPrice = metrics.getSharePrice(end);
        final Double expectedValue = (endPrice - startPrice) / startPrice * 100;
        assertEquals(metrics.getGrowthPercentage(start, end), expectedValue);
    }

    @Test
    void getDividendsPerShare() {
        final Date date = mockDates.get(0);
        final Double expectedValue = mockRawValues.get(0);
        assertEquals(metrics.getDividendsPerShare(date), expectedValue);
    }

    @Test
    void getNonexistentMetricValue() {
        final int lastIndex = mockDates.size() - 1;
        final Date latest = mockDates.get(lastIndex);
        final Date nonexistentDate = addOneDay(latest);

        final Double expectedValue = metrics.getSharePrice(latest);
        assertEquals(expectedValue, metrics.getSharePrice(nonexistentDate));
    }

    private Date addOneDay(Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }
}
