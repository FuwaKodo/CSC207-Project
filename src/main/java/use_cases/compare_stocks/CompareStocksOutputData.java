package main.java.use_cases.compare_stocks;

import main.java.entities.Stock;

/**
 * Data from comparing two stocks is sent through this class to
 * interface adapters.
 */
public class CompareStocksOutputData {
    private final String firstCompanyName;
    private final String secondCompanyName;
    private final Double difference;

    public CompareStocksOutputData(Stock firstStock, Stock secondStock, Double difference) {
        this.firstCompanyName = firstStock.getCompany();
        this.secondCompanyName = secondStock.getCompany();
        this.difference = difference;
    }

    public String getFirstCompanyName() {
        return firstCompanyName;
    }

    public String getSecondCompanyName() {
        return secondCompanyName;
    }

    /**
     * The difference when comparing two stocks in dollars. A positive
     * return value means that the first company is "greater" than the second company
     * in some metric. Otherwise the first company is equal to smaller than the second company.
     * @return the difference in dollars.
     */
    public Double getDifference() {
        return difference;
    }
}
