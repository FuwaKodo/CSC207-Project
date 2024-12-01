package entities;

import java.time.LocalDate;
import java.time.ZoneId;
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
    private final ArrayList<LocalDate> dates;

    /**
     * Constructor.
     * @param values values
     * @param dates date associated with each value, sorted from past to present.
     *              The last element is the latest.
     */
    public MetricValues(List<Double> values, List<LocalDate> dates) {
        this.values = new ArrayList<>(values);
        this.dates = new ArrayList<>(dates);
    }

    /**
     * Get value on a date.
     * @param date the date. If there is no value associated with this date,
     *             then return the value on the closest date.
     * @return the value on day
     */
    public Double getValue(LocalDate date) {
        return values.get(dateToIndex(date));
    }

    /**
     * A sublist of the total values stored.
     * @param start the start of the interval as a date, inclusive.
     * @param end the end of the interval as a date, inclusive.
     * @return a sublist inside the interval
     */
    public List<Double> getInterval(LocalDate start, LocalDate end) {
        final int startIndex = dateToIndex(start);
        final int endIndex = dateToIndex(end) + 1;
        return values.subList(startIndex, endIndex);
    }

    public Double getLatest() {
        return values.getLast();
    }

    private int dateToIndex(LocalDate date) {
        for (int i = 0; i < dates.size(); i++) {
            LocalDate comparisonDate = dates.get(i);
            if (!comparisonDate.isBefore(date)) {
                return i;
            }
        }
        return dates.size() - 1;
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
