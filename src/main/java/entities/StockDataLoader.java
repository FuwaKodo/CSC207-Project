package main.java.entities;

import java.util.List;

public interface StockDataLoader {
    String getCompany();
    String getSymbol();
    List<Double> getSharePrices();
    List<Double> getEarnings();
}
