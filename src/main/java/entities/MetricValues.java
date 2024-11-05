package main.java.entities;

import java.util.List;

/**
 * Stores the values of a metric. For example, the share price of a stock is a metric and its value
 * is the ordered list of share prices over the reporting period.
 */
class MetricValues {
    private final List<Double> values;

    public MetricValues(List<Double> values) {
        this.values = values;
    }

    public Double getValue(int day) {
        final int index = dayToIndex(day);
        return values.get(index);
    }

    /**
     * A sublist of the total values stored.
     * @param startDay the start of the interval in terms of the number of days
     *                 before today, inclusive
     * @param endDay the end of the interval in terms of the number of days before today,
     *               exclusive
     * @return a sublist inside the interval
     */
    public List<Double> getInterval(int startDay, int endDay) {
        final int startIndex = dayToIndex(startDay);
        final int endIndex = dayToIndex(endDay);
        return values.subList(startIndex, endIndex);
    }

    public Double getLatest() {
        return values.getLast();
    }

    private int dayToIndex(int day) {
        return values.size() - day;
    }
}
