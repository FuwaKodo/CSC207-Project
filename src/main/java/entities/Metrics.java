package entities;

import java.time.LocalDate;
import java.util.List;

import app.Constants;

/**
 * Metrics: the metrics of a stock.
 */
public class Metrics {
    private final SharePrices sharePrices;
    private final MetricValues earnings;
    private final MetricValues volumes;
    private final MetricValues dividends;

    public Metrics(SharePrices sharePrices,
                   MetricValues earnings,
                   MetricValues volumes,
                   MetricValues dividendsPerShare) {
        this.sharePrices = sharePrices;
        this.earnings = earnings;
        this.volumes = volumes;
        this.dividends = dividendsPerShare;
    }

    /**
     * Get share price on date.
     * @param date the date. If there is no value for the date, return the value on
     *             the date immediately after it.
     * @return share price
     */
    public Double getSharePrice(LocalDate date) {
        return sharePrices.getValue(date);
    }

    /**
     * Get volume on date.
     * @param date the date. If there is no value for the date, return the value
     *             on the date immedidately after it.
     * @return volume at given days back
     */
    public Double getVolume(LocalDate date) {
        return volumes.getValue(date);
    }

    /**
     * Calculate growth percentage between the stock at the start and
     * end of an interval.
     * @param start the starting date of the interval, inclusive.
     * @param end the end date of the interval, inclusive.
     * @return the growth percentage
     */
    public Double getGrowthPercentage(LocalDate start, LocalDate end) {
        final Double startPrice = sharePrices.getValue(start);
        final Double endPrice = sharePrices.getValue(end);
        return (endPrice - startPrice) / startPrice * Constants.PERCENTAGE;
    }

    /**
     * Calculate earnings per share by aggregating earnings between an interval.
     * @param start the starting date of the interval, inclusive.
     * @param end the end date of the interval, inclusive.
     * @return earnings per share
     */
    public Double getEarningsPerShare(LocalDate start, LocalDate end) {
        return getTotalEarnings(start, end) / sharePrices.getLatest();
    }

    /**
     * Get the dividends per share on a date.
     * @param date the date.
     * @return dividends per share
     */
    public Double getDividendsPerShare(LocalDate date) {
        return dividends.getValue(date);
    }

    private Double getTotalEarnings(LocalDate start, LocalDate end) {
        final List<Double> earningsInInterval = earnings.getInterval(start, end);
        double total = 0;
        for (Double earning : earningsInInterval) {
            total += earning;
        }
        return total;
    }
}
