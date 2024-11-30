package test;

import org.junit.jupiter.api.Test;
import use_cases.text_analyze_stock.AnalyzeStock;
import use_cases.text_analyze_stock.StockAnalysisResult;

import static org.junit.jupiter.api.Assertions.*;

public class TestAnalyzeStock {

    @Test
    public void testCalculateProjectedPrice_AAPL_ValidEPS() {
        String stockName = "AAPL";
        double currentPrice = 237.33;
        double startingPrice = 185;

        // Assuming GetStockeps.geteps("AAPL") returns a valid EPS value
        StockAnalysisResult result = AnalyzeStock.calculateProjectedPrice(stockName, currentPrice, startingPrice);

        // Assertions
        assertNotNull(result);
        assertEquals("AAPL", result.getStockName());
        assertEquals(237.33, result.getCurrentPrice());
        assertTrue(result.getProjectedPrice1() < currentPrice);
        assertTrue(result.getProjectedPrice2() < result.getProjectedPrice1());
        assertTrue(result.getProjectedPrice3() < result.getProjectedPrice2());
        assertEquals("sell", result.getAction());
    }

    @Test
    public void testCalculateProjectedPrice_NVDA_ValidEPS() {
        String stockName = "NVDA";
        double currentPrice = 138.25;
        double startingPrice = 56;

        // Assuming GetStockeps.geteps("NVDA") returns a valid EPS value
        StockAnalysisResult result = AnalyzeStock.calculateProjectedPrice(stockName, currentPrice, startingPrice);

        // Assertions
        assertNotNull(result);
        assertEquals("NVDA", result.getStockName());
        assertEquals(138.25, result.getCurrentPrice());
        assertTrue(result.getProjectedPrice1() < currentPrice);
        assertTrue(result.getProjectedPrice2() < result.getProjectedPrice1());
        assertTrue(result.getProjectedPrice3() < result.getProjectedPrice2());
        assertEquals("sell", result.getAction());
    }

    @Test
    public void testCalculateProjectedPrice_LT_O_InvalidEPS() {
        String stockName = "L.TO";
        double currentPrice = 180.36;
        double startingPrice = 126;

        // Simulating EPS <= 0 or null
        StockAnalysisResult result = AnalyzeStock.calculateProjectedPrice(stockName, currentPrice, startingPrice);

        // Assertions
        assertNotNull(result);
        assertEquals("L.TO", result.getStockName());
        assertEquals(180.36, result.getCurrentPrice());
        assertTrue(result.getProjectedPrice1() < currentPrice);
        assertTrue(result.getProjectedPrice2() < result.getProjectedPrice1());
        assertTrue(result.getProjectedPrice3() < result.getProjectedPrice2());
        assertEquals("sell", result.getAction());
    }

    @Test
    public void testCalculateProjectedPrice_INTC_ValidEPS() {
        String stockName = "INTC";
        double currentPrice = 24.05;
        double startingPrice = 50.00;

        // Assuming GetStockeps.geteps("INTC") returns a valid EPS value
        StockAnalysisResult result = AnalyzeStock.calculateProjectedPrice(stockName, currentPrice, startingPrice);

        // Assertions
        assertNotNull(result);
        assertEquals("INTC", result.getStockName());
        assertEquals(24.05, result.getCurrentPrice());
        assertTrue(result.getProjectedPrice1() < currentPrice);
        assertTrue(result.getProjectedPrice2() < result.getProjectedPrice1());
        assertTrue(result.getProjectedPrice3() < result.getProjectedPrice2());
        assertEquals("sell", result.getAction());
    }

    @Test
    public void testCalculateProjectedPrice_MFC_ValidEPS() {
        String stockName = "MFC";
        double currentPrice = 45.07;
        double startingPrice = 27;

        // Assuming GetStockeps.geteps("INTC") returns a valid EPS value
        StockAnalysisResult result = AnalyzeStock.calculateProjectedPrice(stockName, currentPrice, startingPrice);

        // Assertions
        assertNotNull(result);
        assertEquals("MFC", result.getStockName());
        assertEquals(45.07, result.getCurrentPrice());
        assertTrue(result.getProjectedPrice1() > currentPrice);
        assertTrue(result.getProjectedPrice2() > result.getProjectedPrice1());
        assertTrue(result.getProjectedPrice3() > result.getProjectedPrice2());
        assertEquals("hold", result.getAction());
    }

    @Test
    public void testCalculateProjectedPrice_MFC_ValidEPS_Alternate() {
        String stockName = "MFC";
        double currentPrice = 27;
        double startingPrice = 45.07;

        // Assuming GetStockeps.geteps("INTC") returns a valid EPS value
        StockAnalysisResult result = AnalyzeStock.calculateProjectedPrice(stockName, currentPrice, startingPrice);

        // Assertions
        assertNotNull(result);
        assertEquals("MFC", result.getStockName());
        assertEquals(27, result.getCurrentPrice());
        assertTrue(result.getProjectedPrice1() > currentPrice);
        assertTrue(result.getProjectedPrice2() > result.getProjectedPrice1());
        assertTrue(result.getProjectedPrice3() > result.getProjectedPrice2());
        assertEquals("buy", result.getAction());
    }

}
