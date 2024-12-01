package interface_adapters.text_analyze_stock;

import java.awt.Font;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import app.Constants;
import entities.SharePrices;
import interface_adapters.gateways.StockDataLoader;
import use_cases.StockDataInterface;
import use_cases.text_analyze_stock.AnalyzeStock;
import use_cases.text_analyze_stock.StockAnalysisResult;

/**
 * The StockPresenter class is responsible for performing stock analysis,
 * and displaying the results in a graphical user interface.
 * It uses the AnalyzeStock class to calculate projected stock prices and determine the appropriate action (buy, hold,
 * or sell)
 * based on the analysis results.
 * The main method performs the following steps:
 * 1. Calculates the projected stock prices using the AnalyzeStock.calculateProjectedPrice method.
 * 2. Creates a JFrame to display the stock analysis results.
 * 3. Formats the analysis result into HTML text.
 * 4. Creates a JLabel to present the formatted result text and adds it to the JFrame.
 * 5. Centers the JFrame on the screen and makes it visible.
 */
public class StockPresenter {
    /**
     * The main method executes the stock analysis and displays the results in a graphical user interface.
     * It calculates projected stock prices and determines a suggested action (buy, hold, or sell).
     * A JFrame is created to present the results with formatted HTML text.
     *
     * @param args command line arguments passed to the application.
     */
    public static void main(String[] args) {
        final String stockSymbol = "INTC";
        // Original Date
        final Calendar myCalendarStart = new GregorianCalendar(2023, 10, 6);
        final Date myDateInitial = myCalendarStart.getTime();
        final StockDataInterface stockDataInitial = new StockDataLoader();
        final SharePrices sharePricesInitial = stockDataInitial.getSharePrice(stockSymbol, myDateInitial);
        System.out.println(sharePricesInitial.getHighPrices().get(0));
        // Current Date
        final Calendar myCalendarCurrent = new GregorianCalendar(2024, 10, 5);
        final Date myDateCurrent = myCalendarCurrent.getTime();
        final StockDataInterface stockDataCurrent = new StockDataLoader();
        final SharePrices sharePricesCurrent = stockDataCurrent.getSharePrice(stockSymbol, myDateCurrent);
        final StockAnalysisResult result = AnalyzeStock.calculateProjectedPrice(stockSymbol,
                sharePricesCurrent.getHighPrices().get(0), sharePricesInitial.getHighPrices().get(0));
        System.out.println(sharePricesCurrent.getHighPrices().get(0));
        // Create a new frame to display the result
        final JFrame frame = new JFrame("Stock Analysis Result");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FOUR_HUNDRED, Constants.THREE_HUNDRED);

        // Set variable breaks to the value of <br>
        final String breaks = "<br>";
        // Format the result text
        final String resultText = "<html>"
                +
                "<b>Stock Name:</b> " + result.getStockName() + breaks
                +
                "<b>Current Price:</b> " + result.getCurrentPrice() + breaks
                +
                "<b>Projected Price 1:</b> " + result.getProjectedPrice1() + breaks
                +
                "<b>Projected Price 2:</b> " + result.getProjectedPrice2() + breaks
                +
                "<b>Projected Price 3:</b> " + result.getProjectedPrice3() + breaks
                +
                "<b>Action:</b> " + result.getAction()
                +
                "</html>";

        // Create a label to display the result
        final JLabel label = new JLabel(resultText, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, Constants.SIZE));

        // Add the label to the frame
        frame.getContentPane().add(label);

        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }
}
