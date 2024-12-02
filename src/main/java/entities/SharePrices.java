package entities;

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

        for (int i = 0; i < highPrices.size(); i++) {
            if (highPrices.get(i).equals(Double.NaN)) {
                this.highPrices.remove(i);
                this.lowPrices.remove(i);
                this.closePrices.remove(i);
                this.openPrices.remove(i);
                getDates().remove(i);
            }
        }
    }

    public List<Double> getOpenPrices() {
        return openPrices;
    }

    public List<Double> getClosePrices() {
        return closePrices;
    }

    public List<Double> getHighPrices() {
        return highPrices;
    }

    public List<Double> getLowPrices() {
        return lowPrices;
    }
}
