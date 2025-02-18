package ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.Constants;
import interface_adapters.search.SearchController;
import interface_adapters.search.SearchState;
import interface_adapters.search.SearchViewModel;
import interface_adapters.view_stock.ViewStockController;

/**
 * The view the user sees when using the search feature.
 */
public class SearchView {
    private final JPanel mainPanel;
    private final List<JButton> searchResultButtons;
    private final JPanel pagesPanel;
    private final CardLayout pagesCardLayout;
    private final JLabel info;
    private final JButton searchButton = new JButton("Search");
    private final JTextField searchField = new JTextField(8);
    private final String viewName = Constants.SEARCH_VIEW;

    private final SearchViewModel searchViewModel;
    private final SearchController searchController;
    private final ViewStockController viewStockController;

    public SearchView(SearchViewModel searchViewModel, SearchController searchController,
               ViewStockController viewStockController) {
        this.searchViewModel = searchViewModel;
        this.searchController = searchController;
        this.viewStockController = viewStockController;
        searchResultButtons = new ArrayList<JButton>();
        // Response to clicking searchButton
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // executes search use case with input from searchField
                searchController.execute(searchField.getText());
            }
        });

        // initializes panel
        mainPanel = new JPanel();
        pagesCardLayout = new CardLayout();
        pagesPanel = new JPanel(pagesCardLayout);

        // sets dimensions and stuff of panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setSize(Constants.SEARCH_RESULT_FRAME_DIMENSION);
        pagesPanel.setPreferredSize(Constants.PAGE_DIMENSION);
        pagesPanel.setMaximumSize(Constants.PAGE_DIMENSION);

        // panel for changing page number
        final JPanel buttonPanel = initiateButtonPanel();

        // panel for displaying page number information
        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setPreferredSize(Constants.BUTTON_DIMENSION);
        infoPanel.setMaximumSize(Constants.BUTTON_DIMENSION);
        info = new JLabel();
        infoPanel.add(info);

        // add panels to mainPanel and others for alignment
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(pagesPanel);
        mainPanel.add(Box.createVerticalStrut(Constants.BUTTON_SPACING));
        mainPanel.add(buttonPanel);
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalGlue());

        searchViewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateSearchResult();
                getMainPanel().revalidate();
                getMainPanel().repaint();
            }
        });
    }

    private JPanel initiateButtonPanel() {
        final JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setMaximumSize(Constants.BUTTON_PANEL_DIMENSION);

        // fills button panel with buttons
        final JButton leftPage = new JButton("<");
        leftPage.setBackground(Constants.BUTTON_COLOUR);
        leftPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // checks if not at first page
                if (!(searchViewModel.getState().getIntPageNumber() == 0)) {
                    final SearchState currentState = searchViewModel.getState();
                    currentState.minusPageNumber();
                    pagesCardLayout.show(pagesPanel, currentState.getStringPageNumber());
                    updatePageInfoLabel();
                }
            }
        });
        final JButton rightPage = new JButton(">");
        rightPage.setBackground(Constants.BUTTON_COLOUR);
        rightPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // checks if not at last page
                if (!(searchViewModel.getState().getIntPageNumber() == getNumberOfPages() - 1)) {
                    final SearchState currentState = searchViewModel.getState();
                    currentState.addPageNumber();
                    pagesCardLayout.show(pagesPanel, currentState.getStringPageNumber());
                    updatePageInfoLabel();
                }
            }
        });
        buttonPanel.add(leftPage);
        buttonPanel.add(rightPage);

        return buttonPanel;
    }

    /**
     * Get the number of pages there are currently. Requires searchResultButtons to be updated first.
     * @return total number of pages
     */
    private int getNumberOfPages() {
        return (searchResultButtons.size() + Constants.MAX_STOCK_PER_PAGE - 1) / Constants.MAX_STOCK_PER_PAGE;
    }

    /**
     * Get the String for displaying current page number.
     * @return text to display
     */
    private String getCurrentPage() {
        final int currentPageNumber;
        // set current page number to 0 if there are no pages
        if (getNumberOfPages() > 0) {
            currentPageNumber = searchViewModel.getState().getIntPageNumber() + 1;
        }
        else {
            currentPageNumber = 0;
        }
        return "Page " + String.valueOf(currentPageNumber)
                + " / " + String.valueOf(getNumberOfPages());
    }

    /**
     * Updates the label for displaying page number.
     */
    private void updatePageInfoLabel() {
        info.setText(getCurrentPage());
        info.revalidate();
        info.repaint();
    }

    /**
     * Updates display with search result in searchViewModel.getState().
     */
    public void updateSearchResult() {
        // empties previous search result
        searchResultButtons.clear();
        pagesPanel.removeAll();
        // fills searchResult
        for (int i = 0; i < searchViewModel.getState().getSymbols().size(); i++) {
            // creates new button for a stock
            final JButton stockButton = new JButton(searchViewModel.getState().getSymbols().get(i));
            // sets size and stuff of button
            stockButton.setPreferredSize(Constants.BUTTON_DIMENSION);
            stockButton.setMaximumSize(Constants.BUTTON_DIMENSION);
            stockButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            stockButton.setBackground(Constants.BUTTON_COLOUR);
            // adds action listener to button
            stockButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewStockController.execute(((JButton) e.getSource()).getText());
                }
            });
            searchResultButtons.add(stockButton);
        }

        for (int pageNumber = 0; pageNumber < getNumberOfPages(); pageNumber++) {
            // create a new panel for this page
            final JPanel page = new JPanel();
            page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
            page.setPreferredSize(Constants.PAGE_DIMENSION);
            // fills this page with buttons (up to the MAX_STOCK_PER_PAGE)
            for (int i = 0; i < Constants.MAX_STOCK_PER_PAGE; i++) {
                // checks if current index is in range of searchResultButtons
                if (pageNumber * Constants.MAX_STOCK_PER_PAGE + i < searchResultButtons.size()) {
                    page.add(searchResultButtons.get(pageNumber * Constants.MAX_STOCK_PER_PAGE + i));
                    page.add(Box.createVerticalStrut(Constants.BUTTON_SPACING));
                }
            }
            // container panel for centering page
            final JPanel centeringPanel = new JPanel(new GridBagLayout());
            centeringPanel.add(page);
            pagesPanel.add(centeringPanel, String.valueOf(pageNumber));
        }
        if (getNumberOfPages() <= 0) {
            final JPanel centeringPanel = new JPanel(new GridBagLayout());
            final JLabel noResultsLabel = new JLabel("No results found");
            noResultsLabel.setFont(Constants.NO_RESULTS_FONT);
            centeringPanel.add(noResultsLabel);
            pagesPanel.add(centeringPanel, "0");
        }
        updatePageInfoLabel();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getViewName() {
        return viewName;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }
}
