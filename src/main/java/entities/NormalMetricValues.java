package main.java.entities;

import java.util.List;

/**
 * An implementation of MetricValues.
 */
class NormalMetricValues implements MetricValues {
    private final List<Double> values;

    public NormalMetricValues(List<Double> values) {
        this.values = values;
    }

    @Override
    public Double getValue(int day) {
        final int index = dayToIndex(day);
        return values.get(index);
    }

    @Override
    public List<Double> getInterval(int startDay, int endDay) {
        final int startIndex = dayToIndex(startDay);
        final int endIndex = dayToIndex(endDay);
        return values.subList(startIndex, endIndex);
    }

    @Override
    public Double getLatest() {
        return values.getLast();
    }

    private int dayToIndex(int day) {
        return values.size() - day;
    }
}
