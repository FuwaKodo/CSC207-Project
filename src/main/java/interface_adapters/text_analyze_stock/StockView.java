package interface_adapters.text_analyze_stock;

import app.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * View for displaying stock analysis results.
 */
public class StockView {

    private final JFrame frame;

    public StockView() {
        frame = new JFrame("Stock Analysis Result");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FOUR_HUNDRED, Constants.THREE_HUNDRED);
        frame.setLocationRelativeTo(null); // Center the window
    }

    /**
     * Displays the stock analysis result in the GUI.
     *
     * @param viewModel the ViewModel containing the stock analysis data
     */
    public void display(StockViewModel viewModel) {
        final String breaks = "<br>";
        final String resultText = "<html>"
                + "<b>Stock Name:</b> " + viewModel.getStockName() + breaks
                + "<b>Current Price:</b> " + viewModel.getCurrentPrice() + breaks
                + "<b>Projected Price 1:</b> " + viewModel.getProjectedPrices().get(0) + breaks
                + "<b>Projected Price 2:</b> " + viewModel.getProjectedPrices().get(1) + breaks
                + "<b>Projected Price 3:</b> " + viewModel.getProjectedPrices().get(2) + breaks
                + "<b>Action:</b> " + viewModel.getAction()
                + "</html>";

        final JLabel label = new JLabel(resultText, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, Constants.SIZE));

        frame.getContentPane().add(label);
        frame.setVisible(true);
    }
}