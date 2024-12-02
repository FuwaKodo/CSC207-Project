package test.text_analyze_stock;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import use_cases.text_analyze_stock.GetStockeps;

/**
 * Test class for GetStockeps.
 * Tests the geteps method with various stock ticker inputs.
 */
public class TestGetStockeps {

    /**
     * Test retrieving EPS for valid stock tickers.
     */
    @Test
    public void testGeteps_ValidTicker() {
        assertEquals(6.59, GetStockeps.geteps("AAPL"), 0.0001, "The EPS value for AAPL should be 6.59.");
        assertEquals(2.13, GetStockeps.geteps("NVDA"), 0.0001, "The EPS value for NVDA should be 2.13.");
        assertEquals(2.12, GetStockeps.geteps("MFC"), 0.0001, "The EPS value for MFC should be 2.12.");
        assertEquals(4.59, GetStockeps.geteps("L.TO"), 0.0001, "The EPS value for L.TO should be 4.59.");
        assertEquals(-0.40, GetStockeps.geteps("INTC"), 0.0001, "The EPS value for INTC should be -0.40.");
    }

    /**
     * Test retrieving EPS for an invalid stock ticker.
     */
    @Test
    public void testGeteps_InvalidTicker() {
        assertNull(GetStockeps.geteps("GOOGL"), "The EPS value for an unknown ticker (GOOGL) should be null.");
        assertNull(GetStockeps.geteps("MSFT"), "The EPS value for an unknown ticker (MSFT) should be null.");
    }

    /**
     * Test retrieving EPS with a case-sensitive mismatch.
     */
    @Test
    public void testGeteps_CaseSensitiveTicker() {
        assertNull(GetStockeps.geteps("aapl"), "The EPS value for 'aapl' (case-sensitive mismatch) should be null.");
        assertNull(GetStockeps.geteps("nvda"), "The EPS value for 'nvda' (case-sensitive mismatch) should be null.");
    }

    /**
     * Test retrieving EPS for an empty string.
     */
    @Test
    public void testGeteps_EmptyString() {
        assertNull(GetStockeps.geteps(""), "The EPS value for an empty string should be null.");
    }

    /**
     * Test retrieving EPS for a null input.
     */
    @Test
    public void testGeteps_NullInput() {
        assertNull(GetStockeps.geteps(null), "The EPS value for a null input should be null.");
    }
}