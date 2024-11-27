package ui;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import app.Constants;
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
    private final String viewName = Constants.SEARCH_VIEW;

    private final SearchViewModel searchViewModel;
    private final ViewStockController viewStockController;

    SearchView(SearchViewModel searchViewModel, ViewStockController viewStockController) {
        this.searchViewModel = searchViewModel;
        this.viewStockController = viewStockController;

        // initializes panel
        mainPanel = new JPanel();
        mainPanel.setBackground(Constants.SEARCH_PANEL_COLOUR);
        searchResultButtons = new ArrayList<JButton>();
        pagesCardLayout = new CardLayout();
        pagesPanel = new JPanel(pagesCardLayout);

        // sets dimensions and stuff of panel
        mainPanel.setLayout(new GridLayout(2,
                1));
        mainPanel.setSize(Constants.SEARCH_RESULT_FRAME_DIMENSION);

        // panel for changing page number
        final JPanel buttonPanel = new JPanel(new FlowLayout());
        final JButton leftPage = new JButton("<");
        leftPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // checks if not at first page
                if (!(searchViewModel.getState().getIntPageNumber() == 0)) {
                    final SearchState currentState = searchViewModel.getState();
                    currentState.minusPageNumber();
                    pagesCardLayout.show(pagesPanel, currentState.getStringPageNumber());
                }
            }
        });
        final JButton rightPage = new JButton(">");
        rightPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // checks if not at last page
                if (!(searchViewModel.getState().getIntPageNumber()
                        == searchResultButtons.size() / Constants.MAX_STOCK_PER_PAGE - 1)) {
                    final SearchState currentState = searchViewModel.getState();
                    currentState.addPageNumber();
                    pagesCardLayout.show(pagesPanel, currentState.getStringPageNumber());
                }
            }
        });
        buttonPanel.add(leftPage);
        buttonPanel.add(rightPage);

        // add panels to mainPanel
        mainPanel.add(pagesPanel);
        mainPanel.add(buttonPanel);
    }

    /**
     * Updates display with search result in searchViewModel.getState().
     */
    public void updateSearchResult() {
        // fills searchResult
        System.out.println(searchViewModel.getState().getSymbols().size());
        System.out.println(searchViewModel.getState().getSymbols());
        for (int i = 0; i < searchViewModel.getState().getSymbols().size(); i++) {
            final JButton stockButton = new JButton(searchViewModel.getState().getSymbols().get(i));
            System.out.println("Button added");
            stockButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewStockController.execute(((JButton) e.getSource()).getText());
                }
            });
            searchResultButtons.add(stockButton);
        }

        for (int pageNumber = 0; pageNumber < searchResultButtons.size() / Constants.MAX_STOCK_PER_PAGE; pageNumber++) {
            final JPanel page = new JPanel(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            for (int i = 0; i < Constants.MAX_STOCK_PER_PAGE; i++) {
                page.add(searchResultButtons.get(pageNumber * Constants.MAX_STOCK_PER_PAGE + i));
                System.out.println("Button added to page");
            }
            pagesPanel.add(page, String.valueOf(pageNumber));
            System.out.println("Page added");
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getViewName() {
        return viewName;
    }
}
