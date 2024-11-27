package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Constants for the entire program. Might refactor to individual layers later on.
 */
public class Constants {
    // Calculation related constants
    public static final int PERCENTAGE = 100;
    // public static final int FIVE_YEARS = (int) ChronoUnit.DAYS.between(LocalDate.now(),
    //         LocalDate.now().minusYears(5));
    public static final int FIVE_YEARS = 5 * 365;
    public static final int THREE_YEARS = 3 * 365;
    public static final int ONE_YEAR = 365;

    // view_stock view related constants
    public static final Dimension MAIN_FRAME_DIMENSION = new Dimension(800, 600);
    public static final Dimension MAIN_FRAME_MIN_DIMENSION = new Dimension(600, 400);
    public static final Dimension STOCK_VIEW_DIMENSION = new Dimension(700, 500);
    public static final Dimension VIEWS_MIN_DIMENSION = new Dimension(300, 100);
    public static final Dimension GRAPH_DIMENSION = new Dimension(600, 400);
    // Nice blue color
    public static final Color GRAPH_COLOR = new Color(0, 123, 255);
    public static final Font GRAPH_VALUE_FONT = new Font("Arial", Font.PLAIN, 12);
    public static final String PLACEHOLDER_TEXT = "Stock Statistics Viewer";
    public static final int PLACEHOLDER_FONT_SIZE = 24;
    public static final Font METRICS_FONT = new Font("Arial", Font.PLAIN, 16);

    // search view constants
    public static final Dimension SEARCH_RESULT_FRAME_DIMENSION = new Dimension(400, 600);
    public static final int MAX_STOCK_PER_PAGE = 10;
    public static final Color SEARCH_PANEL_COLOUR = new Color(150, 50, 200);

    // view names in dropdown menu for viewing stock data
    public static final String STOCK_VIEW = "key for stock view";
    public static final String NO_STOCKS_SELECTED = "Select stock";

    // other view names
    public static final String SEARCH_VIEW = "search view state name";

}
