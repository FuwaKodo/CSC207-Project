package ui;

import app.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class FavoritesManager {
    private final Set<String> favoritedStocks;
    private final DefaultListModel<String> favoritesListModel;
    private final JList<String> favoritesList;
    private final JPanel favoritesPanel;
    private final JButton favoriteButton;

    public FavoritesManager() {
        this.favoritedStocks = new HashSet<>();
        this.favoritesListModel = new DefaultListModel<>();
        this.favoritesList = new JList<>(favoritesListModel);
        this.favoritesPanel = createFavoritesPanel();
        this.favoriteButton = createFavoriteButton();
    }

    private JPanel createFavoritesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Favorites"));
        favoritesList.setPreferredSize(new Dimension(100, (int)(Constants.STOCK_VIEW_DIMENSION.height * 0.7)));
        JScrollPane scrollPane = new JScrollPane(favoritesList);
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JButton createFavoriteButton() {
        JButton button = new JButton("☆ Favorite");
        button.setEnabled(false);
        return button;
    }

    public void toggleFavorite(String symbol) {
        if (!symbol.equals(Constants.NO_STOCKS_SELECTED)) {
            if (favoritedStocks.contains(symbol)) {
                favoritedStocks.remove(symbol);
                favoritesListModel.removeElement(symbol);
                favoriteButton.setText("☆ Favorite");
            } else {
                favoritedStocks.add(symbol);
                favoritesListModel.addElement(symbol);
                favoriteButton.setText("★ Favorited");
            }
            updateFavoritesPanel();
        }
    }

    public void updateFavoritesPanel() {
        if (favoritedStocks.isEmpty()) {
            favoritesPanel.setPreferredSize(new Dimension(100, 50));
        } else {
            favoritesPanel.setPreferredSize(new Dimension(100, (int)(Constants.STOCK_VIEW_DIMENSION.height * 0.7)));
        }
        favoritesPanel.revalidate();
        favoritesPanel.repaint();
    }

    public void updateFavoriteButtonState(String symbol) {
        favoriteButton.setEnabled(!symbol.equals(Constants.NO_STOCKS_SELECTED));
        if (favoritedStocks.contains(symbol)) {
            favoriteButton.setText("★ Favorited");
        } else {
            favoriteButton.setText("☆ Favorite");
        }
    }

    public JPanel getFavoritesPanel() {
        return favoritesPanel;
    }

    public JButton getFavoriteButton() {
        return favoriteButton;
    }

    public Set<String> getFavoritedStocks() {
        return new HashSet<>(favoritedStocks);
    }
}