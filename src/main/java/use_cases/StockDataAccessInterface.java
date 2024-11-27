package use_cases;

import entities.Stock;

import java.util.List;

public interface StockDataAccessInterface {
    List<String> getAllCompanyNames();
    Stock getStockByCompany(String name);
}
