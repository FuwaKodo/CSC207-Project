package use_cases.text_analyze_stock;

class StockAnalysisResult {

    private final String stockName;
    private final double currentPrice;
    private final double projectedPrice1;
    private final double projectedPrice2;
    private final double projectedPrice3;
    private final String action;

    public StockAnalysisResult(String stockName, double currentPrice, double projectedPrice1, double projectedPrice2, double projectedPrice3, String action) {
        this.stockName = stockName;
        this.currentPrice = currentPrice;
        this.projectedPrice1 = projectedPrice1;
        this.projectedPrice2 = projectedPrice2;
        this.projectedPrice3 = projectedPrice3;
        this.action = action;
    }

    public String getStockName() {
        return stockName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getProjectedPrice1() {
        return projectedPrice1;
    }

    public double getProjectedPrice2() {
        return projectedPrice2;
    }

    public double getProjectedPrice3() {
        return projectedPrice3;
    }

    public String getAction() {
        return action;
    }
}
