package test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import entities.MetricValues;
import entities.SharePrices;

public class MockMetrics {
    public static List<Date> makeDates() {
        return List.of(
                convertToDate(LocalDate.of(2024, 11, 24)),
                convertToDate(LocalDate.of(2024, 11, 25)),
                convertToDate(LocalDate.of(2024, 11, 26))
        );
    }

    private static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static List<Double> makeRawValues() {
        return List.of(1.0, 2.0, 3.0);
    }

    public static SharePrices makeSharePrices() {
        final List<Double> rawValues = makeRawValues();
        return new SharePrices(
                makeDates(),
                rawValues,
                rawValues,
                rawValues,
                rawValues
        );
    }

    public static MetricValues makeMetricValues() {
        return new MetricValues(
                makeRawValues(),
                makeDates()
        );
    }
}
