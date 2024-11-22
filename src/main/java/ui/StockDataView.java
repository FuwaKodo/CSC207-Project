package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.Constants;

/**
 * The view the user sees when checking the information of a stock (view_stock use case).
 */
public class StockDataView {
    private String symbol;
    private String company;
    private List<Double> sharePrices;
    private final JPanel stockView;
    private final JLabel symbolLabel;
    private final JLabel companyLabel;
    private final StockGraphPanel graphPanel;

    public StockDataView() {
        this.symbol = "";
        this.symbolLabel = new JLabel(String.format("Symbol: %s", symbol));
        symbolLabel.setFont(Constants.METRICS_FONT);
        this.company = "";
        this.companyLabel = new JLabel(String.format("Company: %s", company));
        companyLabel.setFont(Constants.METRICS_FONT);
        this.sharePrices = new ArrayList<>();
        // Add some sample data
        for (int i = 0; i < 10; i++) {
            this.sharePrices.add(100.0 + Math.random() * 20); // Random prices between 100 and 120
        }
        this.stockView = new JPanel();
        this.graphPanel = new StockGraphPanel();
    }

    /**
     * Sets up JPanel for displaying data.
     */
    public void generatePanel() {
        stockView.setLayout(new BoxLayout(stockView, BoxLayout.Y_AXIS));

        // Create a panel for labels
        final JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        // Display company name
        labelPanel.add(companyLabel);
        // Display stock symbol
        labelPanel.add(symbolLabel);

        stockView.add(labelPanel);

        // Add graph panel
        graphPanel.setPreferredSize(Constants.GRAPH_DIMENSION);
        stockView.add(graphPanel);
    }

    public JPanel getStockView() {
        return stockView;
    }

    /**
     * Updates symbol to new symbol.
     * @param symbol new symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
        symbolLabel.setText(String.format("Symbol: %s", symbol));
    }

    /**
     * Updates company name to new company name.
     * @param company new company name
     */
    public void setCompany(String company) {
        this.company = company;
        companyLabel.setText(String.format("Company: %s", company));
    }

    /**
     * Updates share prices to new share prices.
     * @param sharePrices new share prices
     */
    public void setSharePrices(List<Double> sharePrices) {
        this.sharePrices = sharePrices;
        // Refresh the graph when new data is set
        graphPanel.repaint();
    }

    /**
     * Custom panel for drawing the stock price graph.
     */
    private final class StockGraphPanel extends JPanel {
        private static final int PADDING = 50;
        private static final int POINT_SIZE = 4;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (!(sharePrices == null || sharePrices.isEmpty())) {
                paintComponentHelper((Graphics2D) g);
            }
        }

        private void paintComponentHelper(Graphics2D g) {
            final Graphics2D g2d = g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Calculate dimensions
            final int width = getWidth() - 2 * PADDING;
            final int height = getHeight() - 2 * PADDING;

            // Find min and max values
            final double minPrice = sharePrices.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
            final double maxPrice = sharePrices.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
            final double priceRange = maxPrice - minPrice;

            // Draw axes
            g2d.setColor(Color.BLACK);
            // Y-axis
            g2d.drawLine(PADDING, PADDING, PADDING, height + PADDING);
            // X-axis
            g2d.drawLine(PADDING, height + PADDING, width + PADDING, height + PADDING);

            // Draw price labels
            g2d.setFont(Constants.GRAPH_VALUE_FONT);
            for (int i = 0; i <= 5; i++) {
                final double price = minPrice + (priceRange * i / 5);
                final int y = height + PADDING - (int) ((price - minPrice) * height / priceRange);
                g2d.drawString(String.format("%.2f", price), 5, y);
                // Draw horizontal grid lines
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawLine(PADDING, y, width + PADDING, y);
                g2d.setColor(Color.BLACK);
            }

            // Draw time labels
            for (int i = 0; i < sharePrices.size(); i++) {
                if (i % 2 == 0) {
                    // Draw every other label to avoid crowding
                    final int x = PADDING + (i * width / (sharePrices.size() - 1));
                    g2d.drawString("t" + i, x - 5, height + PADDING + 20);
                }
            }

            // Draw the line graph
            g2d.setColor(Constants.GRAPH_COLOR);
            g2d.setStroke(new BasicStroke(2f));

            for (int i = 0; i < sharePrices.size() - 1; i++) {
                final int x1 = PADDING + (i * width / (sharePrices.size() - 1));
                final int y1 = height + PADDING - (int) ((sharePrices.get(i) - minPrice) * height / priceRange);
                final int x2 = PADDING + ((i + 1) * width / (sharePrices.size() - 1));
                final int y2 = height + PADDING - (int) ((sharePrices.get(i + 1) - minPrice) * height / priceRange);

                // Draw line segment
                g2d.drawLine(x1, y1, x2, y2);

                // Draw points
                g2d.fillOval(x1 - POINT_SIZE / 2, y1 - POINT_SIZE / 2, POINT_SIZE, POINT_SIZE);
            }
            // Draw the last point
            final int lastX = PADDING + width;
            final int lastY = height + PADDING
                    - (int) ((sharePrices.get(sharePrices.size() - 1) - minPrice) * height / priceRange);
            g2d.fillOval(lastX - POINT_SIZE / 2, lastY - POINT_SIZE / 2, POINT_SIZE, POINT_SIZE);
        }
    }
}
