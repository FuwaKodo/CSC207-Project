package test.use_cases.favorites;

import frameworks.FavoriteStockData;
import org.junit.Before;
import org.junit.Test;
import use_cases.favorites.FavoriteStockInputData;
import use_cases.favorites.FavoriteStockInteractor;
import use_cases.favorites.FavoriteStockOutputBoundary;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class FavoriteStockInteractorTest {

    private TestFavoriteStockPresenter presenter;
    private FavoriteStockInteractor interactor;

    @Before
    public void setUp() {
        presenter = new TestFavoriteStockPresenter();
        // Since we can't inject the file storage, we'll need to test through the presenter
        interactor = new FavoriteStockInteractor(presenter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_nullPresenter_throwsException() {
        new FavoriteStockInteractor(null);
    }


    @Test
    public void toggleFavorite() {
        // Add a stock to favorites
        String symbol = "GOOGL";
        FavoriteStockInputData inputData = new FavoriteStockInputData(symbol);

        // Act
        interactor.toggleFavorite(inputData);

        // Assert
        assertEquals(symbol, presenter.lastToggledSymbol);
    }

    @Test
    public void testToggleFavorite() {
        // Add multiple stocks and then toggle one off
        String symbol1 = "AAPL";
        String symbol2 = "MSFT";

        // Add two stocks
        interactor.toggleFavorite(new FavoriteStockInputData(symbol1));
        interactor.toggleFavorite(new FavoriteStockInputData(symbol2));

        // Reset presenter
        presenter.lastToggledSymbol = null;
        presenter.lastToggledStatus = false;

        // Remove one stock
        interactor.toggleFavorite(new FavoriteStockInputData(symbol1));

        // Assert
        assertEquals(symbol1, presenter.lastToggledSymbol);
        assertFalse(presenter.lastToggledStatus);
    }

    @Test
    public void testGetFavorites() {
        // Add multiple stocks and retrieve
        String symbol1 = "AAPL";
        String symbol2 = "GOOGL";

        interactor.toggleFavorite(new FavoriteStockInputData(symbol1));
        interactor.toggleFavorite(new FavoriteStockInputData(symbol2));

        // Retrieve favorites
        interactor.getFavorites();

        // Assert
        assertNotNull(presenter.lastPresentedFavorites);
    }

    @Test
    public void testToggleFavorite1() {
        // Test multiple toggles of the same stock
        String symbol = "TSLA";

        // First toggle (add)
        interactor.toggleFavorite(new FavoriteStockInputData(symbol));
        assertTrue(presenter.lastToggledStatus);

        // Reset presenter
        presenter.lastToggledSymbol = null;
        presenter.lastToggledStatus = false;

        // Second toggle (remove)
        interactor.toggleFavorite(new FavoriteStockInputData(symbol));
        assertEquals(symbol, presenter.lastToggledSymbol);
        assertFalse(presenter.lastToggledStatus);
    }

    @Test
    public void testGetFavorites1() {
        // Test getting favorites with multiple stocks
        String[] symbols = {"AAPL", "GOOGL", "MSFT", "AMZN", "FB"};

        // Add multiple stocks
        for (String symbol : symbols) {
            interactor.toggleFavorite(new FavoriteStockInputData(symbol));
        }

        // Retrieve favorites
        interactor.getFavorites();

        // Assert
        assertNotNull(presenter.lastPresentedFavorites);
    }

    // Test double for the presenter
    private static class TestFavoriteStockPresenter implements FavoriteStockOutputBoundary {
        String lastToggledSymbol;
        boolean lastToggledStatus;
        Set<String> lastPresentedFavorites;

        @Override
        public void presentFavoriteToggled(String symbol, boolean isFavorited) {
            this.lastToggledSymbol = symbol;
            this.lastToggledStatus = isFavorited;
        }

        @Override
        public void presentFavorites(Set<String> favorites) {
            this.lastPresentedFavorites = favorites;
        }
    }
}