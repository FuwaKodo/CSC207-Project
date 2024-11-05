package main.java.use_cases.compare_stocks;

import main.java.entities.Stock;

public interface StockComparator {
    int compare(Stock stock1, Stock stock2);
}
