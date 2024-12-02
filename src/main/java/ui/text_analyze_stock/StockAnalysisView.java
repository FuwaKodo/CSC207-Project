package ui.text_analyze_stock;

import app.Constants;
import entities.SharePrices;
import interface_adapters.gateways.StockDataLoader;
import interface_adapters.text_analyze_stock.StockController;
import interface_adapters.text_analyze_stock.StockViewModel;
import interface_adapters.view_stock.ViewStockState;
import interface_adapters.view_stock.ViewStockViewModel;
import use_cases.StockDataInterface;
import use_cases.text_analyze_stock.StockInteractor;
import use_cases.text_analyze_stock.StockAnalysisResult;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class StockAnalysisView {
    private final StockViewModel predictViewModel;
    private final ViewStockViewModel viewModel;
    private final StockController predictController;
    private JLabel label = new JLabel();
    public StockAnalysisView (StockViewModel predictViewModel, ViewStockViewModel viewModel,
                              StockController predictController) {
        this.predictViewModel = predictViewModel;
        this.viewModel = viewModel;
        this.predictController = predictController;
        this.viewModel.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateData();
            }
        });
        // Create a new frame to display the result
        final JFrame frame = new JFrame("Stock Analysis Result");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FOUR_HUNDRED, Constants.THREE_HUNDRED);


        // Create a label to display the result
        label.setFont(new Font("Arial", Font.PLAIN, Constants.SIZE));

        // Add the label to the frame
        frame.getContentPane().add(label);

        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);

    }
    private void updateData(){
        final ViewStockState current_state = viewModel.getState();
        final StockDataLoader stockDataLoader = new StockDataLoader();
        final Calendar myCalendarStart = new GregorianCalendar(2023, 10, 6);
        final Calendar myCalendarCurrent = new GregorianCalendar(2024, 10, 5);
        final Date myDateCurrent = myCalendarCurrent.getTime();
        final Date myDateInitial = myCalendarStart.getTime();
        predictController.execute(current_state.getSymbol(),
                stockDataLoader.getSharePrice(current_state.getSymbol(), myDateCurrent).getHighPrices().get(0),
                stockDataLoader.getSharePrice(current_state.getSymbol(), myDateInitial).getHighPrices().get(0));
        // Set variable breaks to the value of <br>
        final String breaks = "<br>";
        // Format the result text
        label.setText("<html>"
                +
                "<b>Stock Name:</b> " + predictViewModel.getStockName() + breaks
                +
                "<b>Current Price:</b> " + predictViewModel.getCurrentPrice() + breaks
                +
                "<b>Projected Price 1:</b> " + predictViewModel.getProjectedPrices().get(0) + breaks
                +
                "<b>Projected Price 2:</b> " + predictViewModel.getProjectedPrices().get(1) + breaks
                +
                "<b>Projected Price 3:</b> " + predictViewModel.getProjectedPrices().get(2) + breaks
                +
                "<b>Action:</b> " + predictViewModel.getAction()
                +
                "</html>");

    }
}
