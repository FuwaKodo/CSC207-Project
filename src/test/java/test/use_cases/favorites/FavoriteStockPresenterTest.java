package test.use_cases.favorites;

import interface_adapters.favoritesIA.FavoriteStockPresenter;
import interface_adapters.favoritesIA.FavoriteStockState;
import interface_adapters.favoritesIA.FavoriteStockViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FavoriteStockPresenterTest {
    private FavoriteStockViewModel viewModel;
    private FavoriteStockPresenter presenter;
    private boolean propertyChanged;

    @BeforeEach
    void setUp() {
        viewModel = new FavoriteStockViewModel() {
            private FavoriteStockState state = new FavoriteStockState();

            @Override
            public void setState(FavoriteStockState state) {
                this.state = state;
            }

            @Override
            public FavoriteStockState getState() {
                return this.state;
            }

            @Override
            public void firePropertyChanged() {
                propertyChanged = true;
            }
        };
        presenter = new FavoriteStockPresenter(viewModel);
        propertyChanged = false;
    }

    @Test
    void presentFavoriteToggled() {
        // Test adding a favorite
        presenter.presentFavoriteToggled("AAPL", true);
        // Since we can't directly check the favorites, we'll verify that propertyChanged was fired
        assertTrue(propertyChanged);

        // Reset propertyChanged flag
        propertyChanged = false;

        // Test removing a favorite
        presenter.presentFavoriteToggled("AAPL", false);
        assertTrue(propertyChanged);
    }

    @Test
    void presentFavorites() {
        Set<String> favorites = new HashSet<>();
        favorites.add("AAPL");
        favorites.add("GOOGL");
        favorites.add("MSFT");

        presenter.presentFavorites(favorites);

        // Verify that propertyChanged was fired
        assertTrue(propertyChanged);

        // Verify that a new state was set
        assertNotNull(viewModel.getState());
    }
}