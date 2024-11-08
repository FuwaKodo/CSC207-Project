package main.java.entities;

import java.util.Date;
import java.util.List;

/**
 * A subclass of MetricValues. Stores information about share prices.
 */
public class SharePrices extends MetricValues {
    private final List<Double> openPrices;
    private final List<Double> closePrices;
    private final List<Double> highPrices;
    private final List<Double> lowPrices;

    public SharePrices(List<Date> dates,
                       List<Double> openPrices,
                       List<Double> closePrices,
                       List<Double> highPrices,
                       List<Double> lowPrices) {
        super(closePrices, dates);
        this.openPrices = openPrices;
        this.closePrices = closePrices;
        this.highPrices = highPrices;
        this.lowPrices = lowPrices;
    }
}
