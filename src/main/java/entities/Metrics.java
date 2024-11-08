package main.java.entities;

import java.util.List;

/**
 * Metrics: the metrics of a stock.
 */
public class Metrics {
    private final SharePrices sharePrices;
    private final MetricValues earnings;
    private final MetricValues volumes;

    public Metrics(SharePrices sharePrices,
                   MetricValues earnings,
                   MetricValues volumes) {
        this.sharePrices = sharePrices;
        this.earnings = earnings;
        this.volumes = volumes;
    }

    /**
     * Get share price at day. Day parameter represents the number of days before
     * the latest data point.
     * @param day number of days
     * @return share price
     */
    public Double sharePrice(int day) {
        return sharePrices.getValue(day);
    }

    /**
     * Calculate growth percentage between the stock at startDay and
     * stock at endDay.
     * @param startDay the start of the interval in terms of number of days before the latest data point
     * @param endDay the end of the interval exclusive in terms of number of days before the latest data point
     * @return the growth percentage
     */
    public Double growthPercentage(int startDay, int endDay) {
        Double startPrice = sharePrices.getValue(startDay);
        Double endPrice = sharePrices.getValue(endDay);
        return startPrice * 100 / endPrice;
    }

    /**
     * Calculate earnings per share by aggregating earnings between the interval
     * specified by startDay and endDay.
     * @param startDay the start of the interval in terms of number of days before the latest data point
     * @param endDay the end of the interval exclusive in terms of number of days before the latest data point
     * @return earnings per share
     */
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
