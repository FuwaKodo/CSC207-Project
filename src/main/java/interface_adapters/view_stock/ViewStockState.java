package interface_adapters.view_stock;

import entities.MetricValues;
import entities.SharePrices;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the View Stock View Model.
 */
public class ViewStockState {
    private String symbol = "";
    private String company = "";
    private SharePrices sharePrices;
    private MetricValues earnings;
    private String viewStockError;

    public String getSymbol() {
        return symbol;
    }

    public SharePrices getSharePrices() {
        return sharePrices;
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

    public void setSharePrices(SharePrices newSharePrices) {
        this.sharePrices = newSharePrices;
    }

    public void setEarnings(MetricValues newEarnings) {
        this.earnings = newEarnings;
    }

    public void setViewStockError(String newSymbolError) {
        this.viewStockError = newSymbolError;
    }
}
