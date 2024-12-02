package use_cases.view_stock;

import entities.SharePrices;

/**
 * The output data for this use case.
 */
public class ViewStockOutputData {
    private final String company;
    private final String symbol;
    private final SharePrices sharePrices;

    public ViewStockOutputData(String company, String symbol,
                               SharePrices sharePrices) {
        this.company = company;
        this.symbol = symbol;
        this.sharePrices = sharePrices;
    }

    public String getCompany() {
        return company;
    }

    public String getSymbol() {
        return symbol;
    }

    public SharePrices getSharePrices() {
        return sharePrices;
    }
}
