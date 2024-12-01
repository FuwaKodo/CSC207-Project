package test.use_cases.favorites;

import org.junit.Test;
import use_cases.favorites.FavoriteStockInputData;

import static org.junit.Assert.*;

public class FavoriteStockInputDataTest {

    @Test
    public void testValidConstructor() {
        FavoriteStockInputData data = new FavoriteStockInputData("AAPL");
        assertEquals("AAPL", data.getStockSymbol());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSymbol() {
        new FavoriteStockInputData(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptySymbol() {
        new FavoriteStockInputData("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhitespaceSymbol() {
        new FavoriteStockInputData("   ");
    }
}

