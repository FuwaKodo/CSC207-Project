package main.java.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * The view the user sees when checking the information of a stock (view_stock use case).
 */
public class StockDataView {
    private String symbol;
    private String company;
    private List<Double> sharePrices;
    private JPanel stockView;
    private JLabel symbolLabel;
    private JLabel companyLabel;
    private StockGraphPanel graphPanel;

    public StockDataView() {
        this.symbol = "";
        this.symbolLabel = new JLabel(String.format("Symbol: %s", symbol));
        this.company = "";
        this.companyLabel = new JLabel(String.format("Company: %s", company));
        this.sharePrices = new ArrayList<>();
        // Add some sample data
        for (int i = 0; i < 10; i++) {
            this.sharePrices.add(100.0 + Math.random() * 20); // Random prices between 100 and 120
        }
        this.stockView = new JPanel();
        this.graphPanel = new StockGraphPanel();
    }

    public void generatePanel() {
        stockView.setLayout(new BoxLayout(stockView, BoxLayout.Y_AXIS));

        // Create a panel for labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        // Display company name
        labelPanel.add(companyLabel);
        // Display stock symbol
        labelPanel.add(symbolLabel);

        stockView.add(labelPanel);

        // Add graph panel
        graphPanel.setPreferredSize(new Dimension(600, 400));
        stockView.add(graphPanel);
    }

    public JPanel getStockView() {
        return stockView;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
        symbolLabel.setText(String.format("Symbol: %s", symbol));
    }

    public void setCompany(String company) {
        this.company = company;
        companyLabel.setText(String.format("Company: %s", company));
    }

    public void setSharePrices(List<Double> sharePrices) {
        this.sharePrices = sharePrices;
        graphPanel.repaint(); // Refresh the graph when new data is set
    }

    /**
     * Custom panel for drawing the stock price graph
     */
    private class StockGraphPanel extends JPanel {
        private static final int PADDING = 50;
        private static final int POINT_SIZE = 4;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (sharePrices == null || sharePrices.isEmpty()) {
                return;
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Calculate dimensions
            int width = getWidth() - 2 * PADDING;
            int height = getHeight() - 2 * PADDING;

            // Find min and max values
            double minPrice = sharePrices.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
            double maxPrice = sharePrices.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
            double priceRange = maxPrice - minPrice;

            // Draw axes
            g2d.setColor(Color.BLACK);
            // Y-axis
            g2d.drawLine(PADDING, PADDING, PADDING, height + PADDING);
            // X-axis
            g2d.drawLine(PADDING, height + PADDING, width + PADDING, height + PADDING);

            // Draw price labels
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            for (int i = 0; i <= 5; i++) {
                double price = minPrice + (priceRange * i / 5);
                int y = height + PADDING - (int)((price - minPrice) * height / priceRange);
                g2d.drawString(String.format("%.2f", price), 5, y);
                // Draw horizontal grid lines
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawLine(PADDING, y, width + PADDING, y);
                g2d.setColor(Color.BLACK);
            }

            // Draw time labels
            for (int i = 0; i < sharePrices.size(); i++) {
                if (i % 2 == 0) { // Draw every other label to avoid crowding
                    int x = PADDING + (i * width / (sharePrices.size() - 1));
                    g2d.drawString("t" + i, x - 5, height + PADDING + 20);
                }
            }

            // Draw the line graph
            g2d.setColor(new Color(0, 123, 255)); // Nice blue color
            g2d.setStroke(new BasicStroke(2f));

            for (int i = 0; i < sharePrices.size() - 1; i++) {
                int x1 = PADDING + (i * width / (sharePrices.size() - 1));
                int y1 = height + PADDING - (int)((sharePrices.get(i) - minPrice) * height / priceRange);
                int x2 = PADDING + ((i + 1) * width / (sharePrices.size() - 1));
                int y2 = height + PADDING - (int)((sharePrices.get(i + 1) - minPrice) * height / priceRange);

                // Draw line segment
                g2d.drawLine(x1, y1, x2, y2);

                // Draw points
                g2d.fillOval(x1 - POINT_SIZE/2, y1 - POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
            }
            // Draw the last point
            int lastX = PADDING + width;
            int lastY = height + PADDING - (int)((sharePrices.get(sharePrices.size()-1) - minPrice) * height / priceRange);
            g2d.fillOval(lastX - POINT_SIZE/2, lastY - POINT_SIZE/2, POINT_SIZE, POINT_SIZE);
        }
    }
}