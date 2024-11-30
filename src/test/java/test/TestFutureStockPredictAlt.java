package test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import use_cases.text_analyze_stock.FutureStockPredictAlt;

/**
 * Test class for FutureStockPredictAlt.
 * Tests the calculateStockPrice method with different inputs.
 */
public class TestFutureStockPredictAlt {

    /**
     * Test calculateStockPrice with valid inputs.
     */
    @Test
    public void testCalculateStockPrice_ValidInputs() {
        double current = 100.0;
        double initial = 90.0;
        double expected = 100.0 * (1 + (100.0 - 90.0) / 100);  // Expected price calculation

        double actual = FutureStockPredictAlt.calculateStockPrice(current, initial);

        assertEquals(expected, actual, 0.0001, "The stock price calculation should be accurate with valid inputs.");
    }

    /**
     * Test calculateStockPrice with zero current price.
     */
    @Test
    public void testCalculateStockPrice_ZeroCurrentPrice() {
        double current = 0.0;
        double initial = 50.0;
        double expected = 0.0;  // If the current price is zero, the future price will also be zero.

        double actual = FutureStockPredictAlt.calculateStockPrice(current, initial);

        assertEquals(expected, actual, 0.0001, "The stock price should be zero when the current price is zero.");
    }

    /**
     * Test calculateStockPrice with negative current price.
     */
    @Test
    public void testCalculateStockPrice_NegativeCurrentPrice() {
        double current = -100.0;
        double initial = 50.0;


        Double actual = FutureStockPredictAlt.calculateStockPrice(current, initial);

        assertEquals(null, actual, "The stock price calculation should handle negative current prices.");
    }

    /**
     * Test calculateStockPrice with same current and initial price.
     */
    @Test
    public void testCalculateStockPrice_SamePrice() {
        double current = 100.0;
        double initial = 100.0;
        double expected = 100.0 * (1 + (100.0 - 100.0) / 100);  // Expected price is same as current

        double actual = FutureStockPredictAlt.calculateStockPrice(current, initial);

        assertEquals(expected, actual, 0.0001, "The stock price should remain the same when the current and initial prices are equal.");
    }

    /**
     * Test calculateStockPrice with large inputs.
     */
    @Test
    public void testCalculateStockPrice_LargeInputs() {
        double current = 1_000_000.0;
        double initial = 900_000.0;
        double expected = 1_000_000.0 * (1 + (1_000_000.0 - 900_000.0) / 100);  // Large input test

        double actual = FutureStockPredictAlt.calculateStockPrice(current, initial);

        assertEquals(expected, actual, 0.0001, "The stock price calculation should be accurate with large inputs.");
    }

    /**
     * Test calculateStockPrice with small fractional inputs.
     */
    @Test
    public void testCalculateStockPrice_SmallFractionalInputs() {
        double current = 0.01;
        double initial = 0.005;
        double expected = 0.01 * (1 + (0.01 - 0.005) / 100);  // Small fractional inputs test

        double actual = FutureStockPredictAlt.calculateStockPrice(current, initial);

        assertEquals(expected, actual, 0.0001, "The stock price calculation should be accurate with small fractional inputs.");
    }
}
