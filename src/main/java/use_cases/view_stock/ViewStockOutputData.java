package use_cases.view_stock;

import java.util.List;

/**
 * The output data for this use case.
 */
public class ViewStockOutputData {
    private final String company;
    private final String symbol;
    private final List<Double> sharePrices;
    //    private final List<Double> earnings;

    public ViewStockOutputData(String company, String symbol,
                               List<Double> sharePrices/* , List<Double> earnings*/) {
        this.company = company;
        this.symbol = symbol;
        this.sharePrices = sharePrices;
        //        this.earnings = earnings;
    }

    public String getCompany() {
        return company;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<Double> getSharePrices() {
        return sharePrices;
    }

    //    /**
    //     * Return a list of share prices for the given days back in time.
    //     * @param daysBack number of days to back track
    //     * @return list of share prices in given time period
    //    public List<Double> getSharePrices(int daysBack) {
    //        return sharePrices.subList(sharePrices.size() - daysBack, sharePrices.size());
    //    }
    //
    //    public List<Double> getEarnings() {
    //        return earnings;
    //    }
    //
    //    *//**
    //     * Return a list of earnings for the given days back in time.
    //     * @param daysBack number of days to back track
    //     * @return list of earnings in given time period
    //     *//*
    //    public List<Double> getEarnings(int daysBack) {
    //        return earnings.subList(earnings.size() - daysBack, earnings.size());
    //    }*/
}
