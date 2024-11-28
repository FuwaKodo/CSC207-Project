package test;

import entities.MetricValues;
import entities.SharePrices;

import java.time.LocalDate;
import java.util.List;

public class MockMetrics {
    static List<LocalDate> makeDates() {
        return List.of(
                LocalDate.of(2024, 11, 24),
                LocalDate.of(2024, 11, 25),
                LocalDate.of(2024, 11, 26)
        );
    }

    static List<Double> makeRawValues() {
        return List.of(1.0, 2.0, 3.0);
    }

    static SharePrices makeSharePrices() {
        final List<Double> rawValues = makeRawValues();
        return new SharePrices(
                makeDates(),
                rawValues,
                rawValues,
                rawValues,
                rawValues
        );
    }

    static MetricValues makeMetricValues() {
        return new MetricValues(
                makeRawValues(),
                makeDates()
        );
    }
}
