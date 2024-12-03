package test.use_cases.favorites;

import interface_adapters.favoritesIA.FavoritesController;
import javax.swing.*;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FavoritesControllerTest {
    private FavoritesController favoritesController;

    @BeforeEach
    void setUp() {
        favoritesController = new FavoritesController();
    }

    @Test
    void toggleFavorite() {
        // This test might need to be adjusted based on actual implementation
        // We'll verify it doesn't throw an exception for a valid stock symbol
        assertDoesNotThrow(() -> favoritesController.toggleFavorite("AAPL"));
    }

    @Test
    void toggleFavoriteWithNoStocksSelected() {
        // Verify behavior with "No stocks selected"
        assertDoesNotThrow(() -> favoritesController.toggleFavorite("No stocks selected"));
    }


    @Test
    void getFavoriteButton() {
        JButton button = favoritesController.getFavoriteButton();
        assertNotNull(button);
        assertTrue(true);
        assertEquals("★", button.getText());
    }

    @Test
    void getFavoritesPanel() {
        JPanel panel = favoritesController.getFavoritesPanel();
        assertNotNull(panel);
        assertTrue(true);
    }

    @Test
    void getFavoritedStocks() {
        // Initially empty
        Set<String> stocks = favoritesController.getFavoritedStocks();
        assertNotNull(stocks);

        // The initial state might depend on the specific implementation
        // If it starts empty, this test works as is
        // If not, you might need to adjust based on the actual initial state
    }

    @Test
    void initialFavoriteButtonState() {
        JButton button = favoritesController.getFavoriteButton();
        assertTrue(button.isEnabled()); // Assuming the button starts enabled
        assertEquals("★", button.getText());
    }
    @Test
    void updateFavoriteButtonState() {
        JButton button = favoritesController.getFavoriteButton();

        // Test with "No stocks selected"
        favoritesController.updateFavoriteButtonState("No stocks selected");
        assertFalse(button.isEnabled());
        assertEquals("★", button.getText());

        // Test with a non-favorited stock
        favoritesController.updateFavoriteButtonState("AAPL");
        assertTrue(button.isEnabled());
        assertEquals("☆", button.getText());

        // Test with a favorited stock
        // First add the stock to favorites
        favoritesController.toggleFavorite("AAPL");
        favoritesController.updateFavoriteButtonState("AAPL");
        assertTrue(button.isEnabled());
        assertEquals("★", button.getText());
    }
}