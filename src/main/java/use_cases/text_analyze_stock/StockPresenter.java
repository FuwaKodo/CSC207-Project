package use_cases.text_analyze_stock;

class StockPresenter {

    public static void displayStockAnalysis(StockAnalysisResult result) {
        System.out.println("The current price of stock " + result.getStockName() + " is " + result.getCurrentPrice() +
                ". \n"
                + " The projected price of the stock in 1 year is " + result.getProjectedPrice1() + ". \n"
                + " The projected price of the stock in 2 years is " + result.getProjectedPrice2() + ". \n"
                + " The projected price of the stock in 3 years is " + result.getProjectedPrice3() + ". \n"
                + " Based on current trends, this stock is a " + result.getAction());
    }
}