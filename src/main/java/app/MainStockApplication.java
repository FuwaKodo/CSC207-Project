package main.java.app;


import javax.swing.*;
import java.awt.*;


/**
 * Main application for stock analysis and comparison.
 * <p>
 * This application provides functionality to:
 * - View individual stock data and metrics
 * - Compare different stocks using various comparison methods
 * - Analyze stock metrics and share prices
 * </p>
 */
public class MainStockApplication {
    public static void main(String[] args) {
        // Set up the main application frame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Stock Analysis Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            // Initialize ViewStockView and add it to the frame
            ViewStockView viewStockView = new ViewStockView();
            frame.add(viewStockView.getMainPanel());

            // Display the GUI
            frame.setVisible(true);
        });
    }
}
