package main.java.entities;

import java.util.List;

import main.java.Constants;

/**
 * Metrics: the metrics of a stock.
 */
public class Metrics {
    private final MetricValues sharePrices;
    private final MetricValues earnings;

    public Metrics(List<Double> sharePrices, List<Double> earnings) {
        this.sharePrices = new MetricValues(sharePrices);
        this.earnings = new MetricValues(earnings);
    }

    /**
     * Get share price at day. Day parameter represents the number of days before
     * today.
     * @param day number of days before today
     * @return share price
     */
    public Double sharePrice(int day) {
        return sharePrices.getValue(day);
    }

    /**
     * Calculate growth percentage between the stock at startDay and
     * stock at endDay.
     * @param startDay the start of the interval in terms of number of days before today
     * @param endDay the end of the interval exclusive in terms of number of days before today
     * @return the growth percentage
     */
    public Double growthPercentage(int startDay, int endDay) {
        final Double startPrice = sharePrices.getValue(startDay);
        final Double endPrice = sharePrices.getValue(endDay);
        return startPrice * Constants.PERCENTAGE / endPrice;
    }

    /**
     * Calculate earnings per share by aggregating earnings between the interval
     * specified by startTime and endTime.
     * @param startDay the start of the interval in terms of number of days before today
     * @param endDay the end of the interval exclusive in terms of number of days before today
     * @return earnings per share
     */
    public Double earningsPerShare(int startDay, int endDay) {
        return getTotalEarnings(startDay, endDay) / sharePrices.getLatest();
    }

    private Double getTotalEarnings(int startDay, int endDay) {
        final List<Double> earningsInInterval = earnings.getInterval(startDay, endDay);
        double total = 0;
        for (Double earning : earningsInInterval) {
            total += earning;
        }
        return total;
    }
}
