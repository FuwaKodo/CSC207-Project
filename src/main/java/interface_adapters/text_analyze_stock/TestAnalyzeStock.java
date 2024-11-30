package test;

import use_cases.text_analyze_stock.AnalyzeStock;
import use_cases.text_analyze_stock.StockAnalysisResult;

import javax.swing.*;
import java.awt.*;

public class TestAnalyzeStock {

    public static void main(String[] args) {
        // Perform stock analysis
        StockAnalysisResult result = AnalyzeStock.calculateProjectedPrice("L.TO", 180.36, 126);

        // Create a new frame to display the result
        JFrame frame = new JFrame("Stock Analysis Result");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Format the result text
        String resultText = "<html>" +
                "<b>Stock Name:</b> " + result.getStockName() + "<br>" +
                "<b>Current Price:</b> " + result.getCurrentPrice() + "<br>" +
                "<b>Projected Price 1:</b> " + result.getProjectedPrice1() + "<br>" +
                "<b>Projected Price 2:</b> " + result.getProjectedPrice2() + "<br>" +
                "<b>Projected Price 3:</b> " + result.getProjectedPrice3() + "<br>" +
                "<b>Action:</b> " + result.getAction() +
                "</html>";

        // Create a label to display the result
        JLabel label = new JLabel(resultText, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add the label to the frame
        frame.getContentPane().add(label);

        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }
}