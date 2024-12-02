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
    public void toggleFavorite_removeExistingStock_success() {
        // Arrange
        String symbol = "AAPL";
        // First add the stock
        interactor.toggleFavorite(new FavoriteStockInputData(symbol));
        presenter.lastToggledSymbol = null; // Reset the presenter
        presenter.lastToggledStatus = false;

        // Act - toggle again to remove
        interactor.toggleFavorite(new FavoriteStockInputData(symbol));

        // Assert
        assertEquals(symbol, presenter.lastToggledSymbol);
        assertFalse(presenter.lastToggledStatus);
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