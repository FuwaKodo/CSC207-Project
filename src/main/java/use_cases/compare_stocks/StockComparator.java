package use_cases.compare_stocks;

import entities.Stock;

public interface StockComparator {
    int compare(Stock stock1, Stock stock2);
}
