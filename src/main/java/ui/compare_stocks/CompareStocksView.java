package ui.compare_stocks;

import javax.swing.*;

import com.github.lgooddatepicker.components.DatePicker;
import interface_adapters.ViewModel;
import interface_adapters.compare_stocks.CompareStocksController;
import interface_adapters.compare_stocks.CompareStocksState;
import use_cases.compare_stocks.CompareStocksInputData;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

public class CompareStocksView extends JPanel implements PropertyChangeListener {
    private final ViewModel<CompareStocksState> viewModel;
    private final CompareStocksController controller;

    private GroupLayout layout;
    private JComboBox<String> firstStockDropdown;
    private JComboBox<String> secondStockDropdown;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private JTextArea comparisonSummaryDisplay;
    private JButton compareButton;

    public CompareStocksView(CompareStocksController controller, ViewModel<CompareStocksState> viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        layout = new GroupLayout(this);
        this.setLayout(layout);

        compareButton = new JButton("Compare");
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(chooseStocksPanel())
                        .addComponent(pickTimeIntervalPanel())
                        .addComponent(comparisonSummaryComponent())
                        .addComponent(compareButton)
        );

        compareButton.addActionListener(_ -> getNewComparisonSummary());
    }

    private JPanel chooseStocksPanel() {
        final JLabel chooseStocksInstruction = new JLabel("Choose two stocks:");
        firstStockDropdown = new JComboBox<>(controller.getStockNames().toArray(new String[0]));
        secondStockDropdown = new JComboBox<>(controller.getStockNames().toArray(new String[0]));

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(chooseStocksInstruction);
        panel.add(firstStockDropdown);
        panel.add(secondStockDropdown);

        return panel;
    }

    private JPanel pickTimeIntervalPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        final JLabel pickDateInstruction = new JLabel("Choose time interval to compare metrics:");
        panel.add(pickDateInstruction);
        panel.add(datePickerPanel());

        return panel;
    }

    private JTextArea comparisonSummaryComponent() {
        comparisonSummaryDisplay = new JTextArea();
        comparisonSummaryDisplay.setEditable(false);
        return comparisonSummaryDisplay;
    }

    private JPanel datePickerPanel() {
        final JPanel startDatePanel = new JPanel();
        final JLabel startDateLabel = new JLabel("Start date:");

        startDatePicker = new DatePicker();
        startDatePanel.setLayout(new BoxLayout(startDatePanel, BoxLayout.Y_AXIS));
        startDatePanel.add(startDateLabel);
        startDatePanel.add(startDatePicker);

        final JPanel endDatePanel = new JPanel();
        endDatePanel.setLayout(new BoxLayout(endDatePanel, BoxLayout.Y_AXIS));

        final JLabel endDateLabel = new JLabel("End date:");
        endDatePicker = new DatePicker();
        endDatePanel.add(endDateLabel);
        endDatePanel.add(endDatePicker);

        final JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.X_AXIS));
        return parentPanel;
    }

    private void getNewComparisonSummary() {
        final String stock1Name = firstStockDropdown.getSelectedItem().toString();
        final String stock2Name = secondStockDropdown.getSelectedItem().toString();
        final LocalDate start = startDatePicker.getDate();
        final LocalDate end = endDatePicker.getDate();

        final CompareStocksInputData inputData = new CompareStocksInputData(
                stock1Name, stock2Name, start, end
        );
        controller.execute(inputData);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        comparisonSummaryDisplay.setText(viewModel.getState().getSummary());
    }
}
