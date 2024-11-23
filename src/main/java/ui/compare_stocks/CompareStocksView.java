package ui.compare_stocks;

import javax.swing.*;

import com.github.lgooddatepicker.components.DatePicker;
import interface_adapters.ViewModel;
import interface_adapters.compare_stocks.CompareStocksState;

public class CompareStocksView {
    private ViewModel<CompareStocksState> viewModel;

    private JPanel mainPanel;
    private JComboBox<String> firstStockDropdown;
    private JComboBox<String> secondStockDropdown;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private JTextArea comparisonSummaryDisplay;
    private JButton compareButton;

    public CompareStocksView(ViewModel<CompareStocksState> viewModel) {
        this.viewModel = viewModel;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        addChooseStocksComponents();
        addPickTimeIntervalComponents();
        addCompareButton();
        addComparisonSummaryComponent();

        compareButton.addActionListener(_ -> getNewComparisonSummary());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void addChooseStocksComponents() {
        final JLabel chooseStocksInstruction = new JLabel("Choose two stocks:");
        mainPanel.add(chooseStocksInstruction);

        firstStockDropdown = new JComboBox<>(getStockDropdownOptions());
        secondStockDropdown = new JComboBox<>(getStockDropdownOptions());
        mainPanel.add(firstStockDropdown);
        mainPanel.add(secondStockDropdown);
    }

    private void addPickTimeIntervalComponents() {
        final JLabel pickDateInstruction = new JLabel("Choose time interval to compare metrics:");
        mainPanel.add(pickDateInstruction);

        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        mainPanel.add(makeDatePickerLayout(mainPanel));
    }

    private void addComparisonSummaryComponent() {
        comparisonSummaryDisplay = new JTextArea();
        comparisonSummaryDisplay.setEditable(false);
        mainPanel.add(comparisonSummaryDisplay);
    }

    private void addCompareButton() {
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

    private void getNewComparisonSummary() {
        final CompareStocksState state = viewModel.getState();
        state.setFirstStockName(firstStockDropdown.getSelectedItem().toString());
        state.setSecondStockName(secondStockDropdown.getSelectedItem().toString());
        state.setStartDate(startDatePicker.getDate());
        state.setEndDate(endDatePicker.getDate());

        viewModel.firePropertyChanged();
    }

}
