package test.use_cases.favorites;

import interface_adapters.favoritesIA.FavoriteStockState;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class FavoriteStockStateTest {

    @Test
    public void testInitialState() {
        FavoriteStockState state = new FavoriteStockState();
        assertTrue(state.getFavoriteStocks().isEmpty());
    }

    @Test
    public void testAddFavorite() {
        FavoriteStockState state = new FavoriteStockState();
        state.addFavorite("AAPL");
        assertTrue(state.isFavorited("AAPL"));
    }

    @Test
    public void testRemoveFavorite() {
        FavoriteStockState state = new FavoriteStockState();
        state.addFavorite("AAPL");
        state.removeFavorite("AAPL");
        assertFalse(state.isFavorited("AAPL"));
    }

    @Test
    public void testGetFavoriteStocksDefensiveCopy() {
        FavoriteStockState state = new FavoriteStockState();
        state.addFavorite("AAPL");

        Set<String> favorites = state.getFavoriteStocks();
        favorites.add("GOOGL");

        assertFalse(state.isFavorited("GOOGL"));
    }
}