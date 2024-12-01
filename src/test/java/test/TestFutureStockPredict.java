package test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import use_cases.text_analyze_stock.FutureStockPredict;

/**
 * Test class for FutureStockPredict.
 * Tests the calculateGrahamNumber method with different inputs.
 */
public class TestFutureStockPredict {

    /**
     * Test calculateGrahamNumber with valid inputs.
     */
    @Test
    public void testCalculateGrahamNumber_ValidInputs() {
        double eps = 5.0;
        double currentPrice = 100.0;
        double expected = Math.sqrt(22.5 * eps * currentPrice);

        double actual = FutureStockPredict.calculateGrahamNumber(eps, currentPrice);

        assertEquals(expected, actual, 0.0001, "The Graham number calculation should be accurate.");
    }

    /**
     * Test calculateGrahamNumber with zero EPS.
     */
    @Test
    public void testCalculateGrahamNumber_ZeroEPS() {
        double eps = 0.0;
        double currentPrice = 100.0;
        double expected = 0.0;

        double actual = FutureStockPredict.calculateGrahamNumber(eps, currentPrice);

        assertEquals(expected, actual, 0.0001, "The Graham number should be zero when EPS is zero.");
    }

    /**
     * Test calculateGrahamNumber with zero current price.
     */
    @Test
    public void testCalculateGrahamNumber_ZeroCurrentPrice() {
        double eps = 5.0;
        double currentPrice = 0.0;
        double expected = 0.0;

        double actual = FutureStockPredict.calculateGrahamNumber(eps, currentPrice);

        assertEquals(expected, actual, 0.0001, "The Graham number should be zero when the current price is zero.");
    }

    /**
     * Test calculateGrahamNumber with negative inputs.
     */
    @Test
    public void testCalculateGrahamNumber_NegativeInputs() {
        double eps = -5.0;
        double currentPrice = -100.0;

        // Graham's number calculation might yield NaN for negative inputs due to sqrt of a negative number.
        Double actual = FutureStockPredict.calculateGrahamNumber(eps, currentPrice);

        assertEquals(null, actual, "The Graham number should be NaN for negative inputs.");
    }

    /**
     * Test calculateGrahamNumber with small inputs.
     */
    @Test
    public void testCalculateGrahamNumber_SmallInputs() {
        double eps = 0.01;
        double currentPrice = 1.0;
        double expected = Math.sqrt(22.5 * eps * currentPrice);

        double actual = FutureStockPredict.calculateGrahamNumber(eps, currentPrice);

        assertEquals(expected, actual, 0.0001, "The Graham number calculation should be accurate for small inputs.");
    }
}