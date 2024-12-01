public class StockAnalysisApp {
    public static void main(String[] args) {
        // Example inputs
        final String stockSymbol = "INTC";
        final double initialPrice = 35.50; // Example
        final double currentPrice = 40.25; // Example

        // Initialize ViewModel and Presenter
        StockViewModel stockViewModel = new StockViewModel();
        StockPresenter stockPresenter = new StockPresenter(stockViewModel);

        // Perform stock analysis
        stockPresenter.analyzeStock(stockSymbol, currentPrice, initialPrice);

        // Display results (View layer)
        StockState state = stockViewModel.getState();
        String resultText = "<html>"
                + "<b>Stock Name:</b> " + state.getStockName() + "<br>"
                + "<b>Current Price:</b> " + state.getCurrentPrice() + "<br>"
                + "<b>Projected Prices:</b> " + state.getProjectedPrices() + "<br>"
                + "<b>Action:</b> " + state.getAction()
                + "</html>";

        // Create and show a GUI
        javax.swing.SwingUtilities.invokeLater(() -> {
            javax.swing.JFrame frame = new javax.swing.JFrame("Stock Analysis Result");
            frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            frame.setSize(Constants.FOUR_HUNDRED, Constants.THREE_HUNDRED);

            javax.swing.JLabel label = new javax.swing.JLabel(resultText, javax.swing.SwingConstants.CENTER);
            label.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, Constants.SIZE));
            frame.getContentPane().add(label);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
