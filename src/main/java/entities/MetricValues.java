package main.java.entities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Stores the values of a metric. For example, the share price of a stock is a metric and its value
 * is the ordered list of share prices over the reporting period.
 */
public class MetricValues {
    private final ArrayList<Double> values;
    private final ArrayList<Date> dates;

    /**
     * Constructor.
     * @param values values
     * @param dates date associated with each value, sorted from past to present.
     *              The last element is the latest.
     */
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
        return values.get(dayToIndex(day));
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

    private int dayToIndex(int days) {
        final LocalDate daysAsDate = LocalDate.now().minusDays(days);
        for (int i = 0; i < dates.size(); i++) {
            LocalDate comparisonDate = LocalDate.ofInstant(dates.get(i).toInstant(), ZoneId.systemDefault());
            if (!comparisonDate.isBefore(daysAsDate)) {
                return i;
            }
        }
        return dates.size() - 1;
    }
}
