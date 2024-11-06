package main.java.entities;

/**
 * Metrics: the metrics of a stock.
 */
public interface Metrics {

    /**
     * Get share price at day. Day parameter represents the number of days before
     * today.
     * @param day number of days before today
     * @return share price
     */
    Double sharePrice(int day);

    /**
     * Calculate growth percentage between the stock at startDay and
     * stock at endDay.
     * @param startDay the start of the interval in terms of number of days before today
     * @param endDay the end of the interval exclusive in terms of number of days before today
     * @return the growth percentage
     */
    Double growthPercentage(int startDay, int endDay);

    /**
     * Calculate earnings per share by aggregating earnings between the interval
     * specified by startTime and endTime.
     * @param startDay the start of the interval in terms of number of days before today
     * @param endDay the end of the interval exclusive in terms of number of days before today
     * @return earnings per share
     */
    Double earningsPerShare(int startDay, int endDay);
}
