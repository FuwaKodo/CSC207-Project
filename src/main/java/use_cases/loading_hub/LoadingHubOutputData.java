package use_cases.loading_hub;

import entities.MetricValues;
import entities.SharePrices;

/**
 * Output Data for the Loading Hub Use Case.
 */
public class LoadingHubOutputData {
    private String stockSymbol;
    private String companyName;
    private SharePrices sharePrices;
    private MetricValues volumes;
    private MetricValues afterHours;
    private MetricValues preMarket;

    public LoadingHubOutputData(String stockSymbol, String companyName, SharePrices sharePrices, MetricValues volumes,
                                MetricValues afterHours, MetricValues preMarket) {
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.sharePrices = sharePrices;
        this.volumes = volumes;
        this.afterHours = afterHours;
        this.preMarket = preMarket;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public SharePrices getSharePrices() {
        return sharePrices;
    }

    public MetricValues getVolumes() {
        return volumes;
    }

    public MetricValues getAfterHours() {
        return afterHours;
    }

    public MetricValues getPreMarket() {
        return preMarket;
    }
}
