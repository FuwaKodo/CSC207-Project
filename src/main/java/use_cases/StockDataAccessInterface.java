package use_cases;

import java.util.List;

import entities.Stock;

public interface StockDataAccessInterface {
    List<String> getAllCompanyNames();
    Stock getStockByCompany(String name);
}
