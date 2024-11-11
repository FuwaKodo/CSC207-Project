package main.java;

// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;

/**
 * Constants for the entire program. Might refactor to individual layers later on.
 */
public class Constants {
    public static final int PERCENTAGE = 100;
    // public static final int FIVE_YEARS = (int) ChronoUnit.DAYS.between(LocalDate.now(),
    //         LocalDate.now().minusYears(5));
    public static final int FIVE_YEARS = 5 * 365;
    public static final int THREE_YEARS = 3 * 365;
    public static final int ONE_YEAR = 365;

    public static final String VIEW_STOCK = "Stock statistics";
    public static final String PLACE_HOLDER = "View Stock Data";
    public static final String SELECT_STOCK = "Select stock";

}
