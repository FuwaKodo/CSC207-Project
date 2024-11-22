// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;

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

    // GUI related constants
    public static final Dimension MAIN_FRAME_DIMENSION = new Dimension(800, 600);
    public static final Dimension MAIN_FRAME_MIN_DIMENSION = new Dimension(600, 400);
    public static final Dimension STOCK_VIEW_DIMENSION = new Dimension(700, 500);
    public static final Dimension GRAPH_DIMENSION = new Dimension(600, 400);
    public static final Color GRAPH_COLOR = new Color(0, 123, 255); // Nice blue color
    public static final Font GRAPH_VALUE_FONT = new Font("Arial", Font.PLAIN, 12);
    public static final String PLACEHOLDER_TEXT = "Stock Statistics Viewer";
    public static final int PLACEHOLDER_FONT_SIZE = 24;
    public static final Font METRICS_FONT = new Font("Arial", Font.PLAIN, 16);

    // view names in dropdown menu for viewing stock data
    public static final String STOCK_VIEW = "key for stock view";
    public static final String NO_STOCKS_SELECTED = "Select stock";

}
