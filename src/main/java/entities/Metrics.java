package main.java.entities;

import java.util.List;

/**
 * Metrics: the metrics of a stock.
 */
public class Metrics {
    private List<Double> sharePrices;

    public Metrics(List<Double> sharePrices) {
        this.sharePrices = sharePrices;
    }
}