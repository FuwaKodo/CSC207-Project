package main.java.entities;

import java.util.List;

/**
 * Implementation of Metrics interface.
 */
public class NormalMetrics implements Metrics {
    private final MetricValues sharePrices;
    private final MetricValues earnings;

    public NormalMetrics(List<Double> sharePrices, List<Double> earnings) {
        this.sharePrices = new NormalMetricValues(sharePrices);
        this.earnings = new NormalMetricValues(earnings);
    }

    /**
     * Get share price at day. Day parameter represents the number of days before
     * today.
     * @param day number of days before today
     * @return share price
     */
    @Override
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
    @Override
    public Double growthPercentage(int startDay, int endDay) {
        Double startPrice = sharePrices.getValue(startDay);
        Double endPrice = sharePrices.getValue(endDay);
        return startPrice * 100 / endPrice;
    }

    /**
     * Calculate earnings per share by aggregating earnings between the interval
     * specified by startTime and endTime.
     * @param startDay the start of the interval in terms of number of days before today
     * @param endDay the end of the interval exclusive in terms of number of days before today
     * @return earnings per share
     */
    @Override
    public Double earningsPerShare(int startDay, int endDay) {
        return getTotalEarnings(startDay, endDay) / sharePrices.getLatest();
    }

    private Double getTotalEarnings(int startDay, int endDay) {
        List<Double> earningsInInterval = earnings.getInterval(startDay, endDay);
        double total = 0;
        for (Double earning : earningsInInterval) {
            total += earning;
        }
        return total;
    }
}
