package main.java.entities;

import java.util.List;

public interface MetricValues {
    /**
     * Get value on day. `day` parameter represents the number of days before today.
     *
     * @param day number of days before today
     * @return the value on day
     */
    Double getValue(int day);

    /**
     * A sublist of the total values stored.
     *
     * @param startDay the start of the interval in terms of the number of days
     *                 before today, inclusive
     * @param endDay   the end of the interval in terms of the number of days before today,
     *                 exclusive
     * @return a sublist inside the interval
     */
    List<Double> getInterval(int startDay, int endDay);

    Double getLatest();
}
