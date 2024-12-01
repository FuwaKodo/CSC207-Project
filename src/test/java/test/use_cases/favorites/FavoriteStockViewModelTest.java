package test.use_cases.favorites;

import interface_adapters.favoritesIA.FavoriteStockState;
import interface_adapters.favoritesIA.FavoriteStockViewModel;
import org.junit.Test;
import java.beans.PropertyChangeListener;
import static org.junit.Assert.*;

public class FavoriteStockViewModelTest {

    @Test
    public void testInitialState() {
        FavoriteStockViewModel viewModel = new FavoriteStockViewModel();
        assertNotNull(viewModel.getState());
    }

    @Test
    public void testSetState() {
        FavoriteStockViewModel viewModel = new FavoriteStockViewModel();
        FavoriteStockState newState = new FavoriteStockState();
        newState.addFavorite("AAPL");

        viewModel.setState(newState);
        assertTrue(viewModel.getState().isFavorited("AAPL"));
    }
}

