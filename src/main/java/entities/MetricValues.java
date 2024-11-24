package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Stores the values of a metric. For example, the share price of a stock is a metric and its value
 * is the ordered list of share prices over the reporting period.
 */
public class MetricValues {
    private final ArrayList<Double> values;
    private final ArrayList<Date> dates;

    public MetricValues(List<Double> values, List<Date> dates) {
        this.values = new ArrayList<>(values);
        this.dates = new ArrayList<>(dates);
    }

    /**
     * Get value on day.
     * @param day number of days before the latest data point
     * @return the value on day
     */
    public Double getValue(int day) {
        final int index = dayToIndex(day);
        return values.get(index);
    }

    /**
     * A sublist of the total values stored.
     * @param startDay the start of the interval in terms of the number of days
     *                 before the latest data point, inclusive
     * @param endDay the end of the interval in terms of the number of days before the latest data point,
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

    @Override
    public boolean equals(Object obj) {
        final MetricValues other = (MetricValues) obj;
        boolean state = Objects.equals(values, other.values) && Objects.equals(dates, other.dates);
        if (this == obj) {
            state = true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            state = false;
        }

        return state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(values, dates);
    }
}
