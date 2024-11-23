package ui.compare_stocks;

import javax.swing.*;

import com.github.lgooddatepicker.components.DatePicker;
import interface_adapters.ViewModel;
import interface_adapters.compare_stocks.CompareStocksState;

public class CompareStocksView {
    private ViewModel<CompareStocksState> viewModel;

    private JComboBox<String> firstStockDropdown;
    private JComboBox<String> secondStockDropdown;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private JTextArea comparisonSummaryDisplay;
    private JButton compareButton;

    public CompareStocksView(ViewModel<CompareStocksState> viewModel) {
        this.viewModel = viewModel;

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        addChooseStocksComponents(mainPanel);
        addPickTimeIntervalComponents(mainPanel);
        addCompareButton(mainPanel);
        addComparisonSummaryComponent(mainPanel);

        compareButton.addActionListener(_ -> updateComparisonSummary());
    }

    private void addChooseStocksComponents(JPanel mainPanel) {
        final JLabel chooseStocksInstruction = new JLabel("Choose two stocks:");
        mainPanel.add(chooseStocksInstruction);

        firstStockDropdown = new JComboBox<>(getStockDropdownOptions());
        secondStockDropdown = new JComboBox<>(getStockDropdownOptions());
        mainPanel.add(firstStockDropdown);
        mainPanel.add(secondStockDropdown);
    }

    private void addPickTimeIntervalComponents(JPanel mainPanel) {
        final JLabel pickDateInstruction = new JLabel("Choose time interval to compare metrics:");
        mainPanel.add(pickDateInstruction);

        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        mainPanel.add(makeDatePickerLayout(mainPanel));
    }

    private void addComparisonSummaryComponent(JPanel mainPanel) {
        comparisonSummaryDisplay = new JTextArea();
        comparisonSummaryDisplay.setEditable(false);
        mainPanel.add(comparisonSummaryDisplay);
    }

    private void addCompareButton(JPanel mainPanel) {
        compareButton = new JButton("Compare");
        mainPanel.add(compareButton);
    }

    private String[] getStockDropdownOptions() {
        return new String[] {"Stock A", "Stock B", "Stock C"};
    }

    private JPanel makeDatePickerLayout(JPanel container) {
        final JPanel startDatePanel = new JPanel(new BoxLayout(container, BoxLayout.Y_AXIS));
        final JLabel startDateLabel = new JLabel("Start date:");
        startDatePanel.add(startDateLabel);
        startDatePanel.add(startDatePicker);

        final JPanel endDatePanel = new JPanel(new BoxLayout(container, BoxLayout.Y_AXIS));
        final JLabel endDateLabel = new JLabel("End date:");
        endDatePanel.add(endDateLabel);
        endDatePanel.add(endDatePicker);

        final JPanel layout = new JPanel(new BoxLayout(container, BoxLayout.X_AXIS));
        layout.add(startDatePanel);
        layout.add(endDatePanel);

        return layout;
    }

    private void updateComparisonSummary() {

    }

}
