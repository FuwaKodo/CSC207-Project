package use_cases.text_analyze_stock;

import java.util.HashMap;

public class StockEPSConstant {

    // Create a HashMap to store the stock ticker as key and EPS as value
    private static HashMap<String, Double> stockEPS = new HashMap<>();

    // Static block to initialize the stockEPS map
    static {
        stockEPS.put("AAPL", 6.59);  // Apple
        stockEPS.put("NVDA", 1.91);  // Nvidia
        stockEPS.put("MFC", 2.12);  // Manulife
        stockEPS.put("L.TO", 4.59);  // Loblaws
        stockEPS.put("INTC", -0.40);  // Intel
    }

    // Getter method to return EPS for a given stock ticker
    public static Double getEPS(String stock) {
        return stockEPS.get(stock); // Returns EPS or null if stock not found
    }
}
