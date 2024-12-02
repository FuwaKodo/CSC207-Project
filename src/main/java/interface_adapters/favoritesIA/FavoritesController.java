package interface_adapters.favoritesIA;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import use_cases.favorites.FavoriteStockInputData;
import use_cases.favorites.FavoriteStockInteractor;

/**
 * Controller class for managing favorite stocks functionality.
 */
public class FavoritesController {
    /** Width of the favorites panel. */
    private static final int PANEL_WIDTH = 150;
    /** Height of the favorites panel. */
    private static final int PANEL_HEIGHT = 300;
    /** Vertical spacing between favorite items. */
    private static final int HEIGHT = 5;
    /** Star symbol for favorite button when selected. */
    private static final String STAR_FILLED = "\u2605";
    /** Star symbol for favorite button when not selected. */
    private static final String STAR_EMPTY = "\u2606";
    /** Message displayed when no stock is selected. */
    private static final String NO_STOCK_SELECTED = "No stocks selected";

    private final JButton favoriteButton;
    private final JPanel favoritesPanel;
    private final Set<String> favoritedStocks;
    private final FavoriteStockInteractor favoriteStockInteractor;

    public FavoritesController() {
        this.favoriteButton = new JButton(STAR_EMPTY);
        this.favoritesPanel = new JPanel();
        this.favoritesPanel.setLayout(new BoxLayout(favoritesPanel, BoxLayout.Y_AXIS));
        this.favoritedStocks = new HashSet<>();

        // Create the ViewModel and add a listener for updates
        final FavoriteStockViewModel viewModel = new FavoriteStockViewModel();
        final PropertyChangeListener viewModelListener = evt -> {
            if (evt.getPropertyName().equals(FavoriteStockViewModel.FAVORITES_CHANGED)) {
                final FavoriteStockState state = (FavoriteStockState) evt.getNewValue();
                favoritedStocks.clear();
                favoritedStocks.addAll(state.getFavoriteStocks());
                updateFavoritesPanel();
            }
        };
        viewModel.addPropertyChangeListener(viewModelListener);

        // Create the presenter and interactor
        final FavoriteStockPresenter presenter = new FavoriteStockPresenter(viewModel);
        this.favoriteStockInteractor = new FavoriteStockInteractor(presenter);

        // Load initial favorites
        favoriteStockInteractor.getFavorites();

        setupFavoritesPanel();
    }

    private void setupFavoritesPanel() {
        favoritesPanel.setBorder(BorderFactory.createTitledBorder("Favorites"));
        favoritesPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        updateFavoritesPanel();
    }

    /**
     * Toggles the favorite status of a stock symbol.
     *
     * @param symbol The stock symbol to toggle
     */
    public void toggleFavorite(final String symbol) {
        if (!NO_STOCK_SELECTED.equals(symbol)) {
            favoriteStockInteractor.toggleFavorite(new FavoriteStockInputData(symbol));
        }
    }

    /**
     * Updates the state of the favorite button based on the selected symbol.
     *
     * @param symbol The stock symbol to update the button state for
     */
    public void updateFavoriteButtonState(final String symbol) {
        if (NO_STOCK_SELECTED.equals(symbol)) {
            favoriteButton.setEnabled(false);
            favoriteButton.setText(STAR_EMPTY);
        }
        else {
            favoriteButton.setEnabled(true);
            final String buttonText;
            if (favoritedStocks.contains(symbol)) {
                buttonText = STAR_FILLED;
            }
            else {
                buttonText = STAR_EMPTY;
            }
            favoriteButton.setText(buttonText);
        }
    }

    private void updateFavoritesPanel() {
        favoritesPanel.removeAll();
        for (String stock : favoritedStocks) {
            final JLabel label = new JLabel(stock);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            favoritesPanel.add(label);
            favoritesPanel.add(Box.createVerticalStrut(HEIGHT));
        }
        favoritesPanel.revalidate();
        favoritesPanel.repaint();
    }

    public JButton getFavoriteButton() {
        return favoriteButton;
    }

    public JPanel getFavoritesPanel() {
        return favoritesPanel;
    }

    public Set<String> getFavoritedStocks() {
        return new HashSet<>(favoritedStocks);
    }
}
