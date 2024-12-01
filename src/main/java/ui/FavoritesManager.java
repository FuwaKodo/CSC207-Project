package ui;

import interface_adapters.favoritesIA.FavoriteStockPresenter;
import interface_adapters.favoritesIA.FavoriteStockState;
import interface_adapters.favoritesIA.FavoriteStockViewModel;
import use_cases.favorites.*;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

public class FavoritesManager {
    private final JButton favoriteButton;
    private final JPanel favoritesPanel;
    private final Set<String> favoritedStocks;
    private final FavoriteStockInteractor favoriteStockInteractor;

    public FavoritesManager() {
        this.favoriteButton = new JButton("★");
        this.favoritesPanel = new JPanel();
        this.favoritesPanel.setLayout(new BoxLayout(favoritesPanel, BoxLayout.Y_AXIS));
        this.favoritedStocks = new HashSet<>();

        // Create the ViewModel and add a listener for updates
        FavoriteStockViewModel viewModel = new FavoriteStockViewModel();
        PropertyChangeListener viewModelListener = evt -> {
            if (evt.getPropertyName().equals(FavoriteStockViewModel.FAVORITES_CHANGED)) {
                FavoriteStockState state = (FavoriteStockState) evt.getNewValue();
                favoritedStocks.clear();
                favoritedStocks.addAll(state.getFavoriteStocks());
                updateFavoritesPanel();
            }
        };
        viewModel.addPropertyChangeListener(viewModelListener);

        // Create the presenter and interactor
        FavoriteStockPresenter presenter = new FavoriteStockPresenter(viewModel);
        this.favoriteStockInteractor = new FavoriteStockInteractor(presenter);

        // Load initial favorites
        favoriteStockInteractor.getFavorites();

        setupFavoritesPanel();
    }

    private void setupFavoritesPanel() {
        favoritesPanel.setBorder(BorderFactory.createTitledBorder("Favorites"));
        favoritesPanel.setPreferredSize(new Dimension(150, 300));
        updateFavoritesPanel();
    }

    public void toggleFavorite(String symbol) {
        if (!symbol.equals("No stocks selected")) {
            favoriteStockInteractor.toggleFavorite(new FavoriteStockInputData(symbol));
        }
    }

    public void updateFavoriteButtonState(String symbol) {
        if (symbol.equals("No stocks selected")) {
            favoriteButton.setEnabled(false);
            favoriteButton.setText("★");
        } else {
            favoriteButton.setEnabled(true);
            favoriteButton.setText(favoritedStocks.contains(symbol) ? "★" : "☆");
        }
    }

    private void updateFavoritesPanel() {
        favoritesPanel.removeAll();
        for (String stock : favoritedStocks) {
            JLabel label = new JLabel(stock);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            favoritesPanel.add(label);
            favoritesPanel.add(Box.createVerticalStrut(5));
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
