package main.java.interface_adapters.view_stock;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the View Stock View Model.
 */
public class ViewStockState {
    private String symbol = "";
    private String company = "";
    private List<Double> sharePrices = new ArrayList<>();
    private List<Double> earnings = new ArrayList<>();
    private String viewStockError;

    public String getSymbol() {
        return symbol;
    }

    public String getCompany() {
        return company;
    }

    public List<Double> getSharePrices() {
        return sharePrices;
    }

    /**
     * Return a list of share prices for the given days back in time.
     * @param daysBack number of days to back track
     * @return list of share prices in given time period
     */
    public List<Double> getSharePrices(int daysBack) {
        return sharePrices.subList(sharePrices.size() - daysBack, sharePrices.size());
    }

    public List<Double> getEarnings() {
        return earnings;
    }

    /**
     * Return a list of earnings for the given days back in time.
     * @param daysBack number of days to back track
     * @return list of earnings in given time period
     */
    public List<Double> getEarnings(int daysBack) {
        return earnings.subList(earnings.size() - daysBack, earnings.size());
    }

    public String getViewStockError() {
        return viewStockError;
    }

    public void setSymbol(String newSymbol) {
        this.symbol = newSymbol;
    }

    public void setCompany(String newCompany) {
        this.company = newCompany;
    }

    public void setSharePrices(List<Double> newSharePrices) {
        this.sharePrices = newSharePrices;
    }

    public void setEarnings(List<Double> newEarnings) {
        this.earnings = newEarnings;
    }

    public void setViewStockError(String newSymbolError) {
        this.viewStockError = newSymbolError;
    }

}
