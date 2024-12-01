package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import use_cases.text_analyze_stock.StockAnalysisResult;

/**
 * Test class for StockAnalysisResult.
 * Validates the constructor and getter methods.
 */
public class TestStockAnalysisResult {

    /**
     * Test the constructor and getters for valid inputs.
     */
    @Test
    public void testConstructorAndGetters_ValidInput() {
        // Arrange
        String stockName = "AAPL";
        double currentPrice = 150.0;
        double projectedPrice1 = 160.0;
        double projectedPrice2 = 170.0;
        double projectedPrice3 = 180.0;
        String action = "Buy";

        // Act
        StockAnalysisResult result = new StockAnalysisResult(stockName, currentPrice, projectedPrice1,
                projectedPrice2, projectedPrice3, action);

        // Assert
        assertEquals(stockName, result.getStockName(), "Stock name should match the input.");
        assertEquals(currentPrice, result.getCurrentPrice(), 0.0001, "Current price should match the " +
                "input.");
        assertEquals(projectedPrice1, result.getProjectedPrice1(), 0.0001, "Projected price 1 should " +
                "match the input.");
        assertEquals(projectedPrice2, result.getProjectedPrice2(), 0.0001, "Projected price 2 should " +
                "match the input.");
        assertEquals(projectedPrice3, result.getProjectedPrice3(), 0.0001, "Projected price 3 should " +
                "match the input.");
        assertEquals(action, result.getAction(), "Action should match the input.");
    }

    /**
     * Test creating an object with zero and negative prices to ensure no exceptions are thrown.
     */
    @Test
    public void testConstructor_ZeroAndNegativePrices() {
        // Arrange
        String stockName = "NVDA";
        double currentPrice = 0.0;
        double projectedPrice1 = -10.0;
        double projectedPrice2 = -20.0;
        double projectedPrice3 = 0.0;
        String action = "Hold";

        // Act
        StockAnalysisResult result = new StockAnalysisResult(stockName, currentPrice, projectedPrice1,
                projectedPrice2, projectedPrice3, action);

        // Assert
        assertEquals(stockName, result.getStockName(), "Stock name should match the input.");
        assertEquals(currentPrice, result.getCurrentPrice(), 0.0001, "Current price should match the " +
                "input.");
        assertEquals(projectedPrice1, result.getProjectedPrice1(), 0.0001, "Projected price 1 should " +
                "match the input.");
        assertEquals(projectedPrice2, result.getProjectedPrice2(), 0.0001, "Projected price 2 should " +
                "match the input.");
        assertEquals(projectedPrice3, result.getProjectedPrice3(), 0.0001, "Projected price 3 should " +
                "match the input.");
        assertEquals(action, result.getAction(), "Action should match the input.");
    }

    /**
     * Test creating an object with empty and null stock name or action.
     */
    @Test
    public void testConstructor_EmptyAndNullStrings() {
        // Arrange
        String stockName = "";
        double currentPrice = 100.0;
        double projectedPrice1 = 110.0;
        double projectedPrice2 = 120.0;
        double projectedPrice3 = 130.0;
        String action = null;

        // Act
        StockAnalysisResult result = new StockAnalysisResult(stockName, currentPrice, projectedPrice1,
                projectedPrice2, projectedPrice3, action);

        // Assert
        assertEquals(stockName, result.getStockName(), "Stock name should match the input.");
        assertEquals(currentPrice, result.getCurrentPrice(), 0.0001, "Current price should match the " +
                "input.");
        assertEquals(projectedPrice1, result.getProjectedPrice1(), 0.0001, "Projected price 1 should " +
                "match the input.");
        assertEquals(projectedPrice2, result.getProjectedPrice2(), 0.0001, "Projected price 2 should " +
                "match the input.");
        assertEquals(projectedPrice3, result.getProjectedPrice3(), 0.0001, "Projected price 3 should " +
                "match the input.");
        assertNull(result.getAction(), "Action should be null as provided.");
    }

    /**
     * Test creating an object with maximum and minimum double values.
     */
    @Test
    public void testConstructor_MaxAndMinDoubleValues() {
        // Arrange
        String stockName = "TSLA";
        double currentPrice = Double.MAX_VALUE;
        double projectedPrice1 = Double.MIN_VALUE;
        double projectedPrice2 = Double.MAX_VALUE;
        double projectedPrice3 = Double.MIN_VALUE;
        String action = "Sell";

        // Act
        StockAnalysisResult result = new StockAnalysisResult(stockName, currentPrice, projectedPrice1,
                projectedPrice2, projectedPrice3, action);

        // Assert
        assertEquals(stockName, result.getStockName(), "Stock name should match the input.");
        assertEquals(currentPrice, result.getCurrentPrice(), 0.0001, "Current price should match the " +
                "input.");
        assertEquals(projectedPrice1, result.getProjectedPrice1(), 0.0001, "Projected price 1 should " +
                "match the input.");
        assertEquals(projectedPrice2, result.getProjectedPrice2(), 0.0001, "Projected price 2 should " +
                "match the input.");
        assertEquals(projectedPrice3, result.getProjectedPrice3(), 0.0001, "Projected price 3 should " +
                "match the input.");
        assertEquals(action, result.getAction(), "Action should match the input.");
    }
}