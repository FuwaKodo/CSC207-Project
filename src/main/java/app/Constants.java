package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Constants for the entire program. Might refactor to individual layers later on.
 */
public final class Constants {
    // Calculation related constants
    public static final int PERCENTAGE = 100;
    public static final int ONE_YEAR = 365;
    public static final int ONE_MONTH = 30;
    public static int HALF_MONTH = 10;
    public static final int ONE_WEEK = 7;

    // Display font
    public static final String FONT = "Arial";

    // view_stock view related constants
    public static final Dimension MAIN_FRAME_DIMENSION = new Dimension(800, 600);
    public static final Dimension MAIN_FRAME_MIN_DIMENSION = new Dimension(600, 400);
    public static final Dimension STOCK_VIEW_DIMENSION = new Dimension(700, 500);
    public static final Dimension VIEWS_MIN_DIMENSION = new Dimension(300, 100);
    public static final Dimension GRAPH_DIMENSION = new Dimension(600, 400);
    public static final Color GRAPH_COLOR = new Color(0, 123, 255);
    public static final Font GRAPH_VALUE_FONT = new Font(FONT, Font.PLAIN, 12);
    public static final String PLACEHOLDER_TEXT = "Stock Statistics Viewer";
    public static final Font PLACEHOLDER_FONT = new Font(FONT, Font.BOLD, 24);
    public static final Font METRICS_FONT = new Font(FONT, Font.PLAIN, 16);
    // view names in dropdown menu for viewing stock data
    public static final String STOCK_VIEW = "key for stock view";
    public static final String NO_STOCKS_SELECTED = "Select stock";
    // other view names
    public static final String SEARCH_VIEW = "search view state name";
    // view for load hub
    public static final int LATEST_YEAR = 2023;
    public static final int DATE_SELECTOR_GRID_ROWS = 1;
    public static final int DATE_SELECTOR_GRID_COLS = 3;
    public static final int DATE_SELECTOR_GRID_HGAP = 5;
    public static final int DATE_SELECTOR_GRID_VGAP = 5;

    // search view constants
    public static final int MAX_STOCK_PER_PAGE = 5;
    public static final int BUTTON_SPACING = 10;
    public static final Dimension SEARCH_RESULT_FRAME_DIMENSION = new Dimension(400, 600);
    public static final Dimension BUTTON_DIMENSION = new Dimension(150, 30);
    public static final Dimension BUTTON_PANEL_DIMENSION = new Dimension(350, BUTTON_DIMENSION.height);
    public static final Dimension PAGE_DIMENSION = new Dimension(BUTTON_DIMENSION.width,
                    MAX_STOCK_PER_PAGE * (BUTTON_DIMENSION.height + BUTTON_SPACING));
    public static final Color BUTTON_COLOUR = new Color(220, 220, 245);
    public static final Font NO_RESULTS_FONT = new Font(FONT, Font.PLAIN, 16);
    // search use case constants
    public static final int NOT_SIMILAR = 0;
    public static final int SIMILAR_BY_SOME_CHAR = 1;
    public static final int SIMILAR_AS_SUBSTRING = 2;
    public static final int EXACTLY_SAME = 3;

    // constants for favourite stock use case
    public static final String NOT_FAVORITED = "☆ Favorite";
    public static final String FAVORITED = "★ Favorited";

    //
    public static final int WANTED_DIFFERENCE = 3;
    public static final int WANTED_DIFFERENCE_NEGATIVE = 3;
    public static final double GRAHAM_NUMBER = 22.5;
    public static final int ONE_HUNDRED = 100;
    public static final int THREE_HUNDRED = 300;
    public static final int FOUR_HUNDRED = 400;
    public static final int SIZE = 17;
    // prevents instantiation
    private Constants() {

    }
}
