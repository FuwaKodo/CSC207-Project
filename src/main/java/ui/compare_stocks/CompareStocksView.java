package ui.compare_stocks;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.github.lgooddatepicker.components.DatePicker;
import interface_adapters.ViewModel;
import interface_adapters.compare_stocks.CompareStocksController;
import interface_adapters.compare_stocks.CompareStocksState;
import use_cases.compare_stocks.CompareStocksInputData;

/**
 * A user interface component for comparing stocks.
 * This class allows users to select stocks, specify a date range,
 * and view the comparison results.
 */
public class CompareStocksView extends JPanel implements PropertyChangeListener {
    private static final int DATE_PANEL_HEIGHT = 60;

    private final ViewModel<CompareStocksState> viewModel;
    private final CompareStocksController controller;

    private JComboBox<String> firstStockDropdown;
    private JComboBox<String> secondStockDropdown;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private JTextArea comparisonSummaryDisplay;

    /**
     * Constructs the CompareStocksView with the given controller and view model.
     *
     * @param controller the controller for handling stock comparison logic (cannot be null)
     * @param viewModel  the view model for observing changes to the comparison state (cannot be null)
     */
    public CompareStocksView(CompareStocksController controller, ViewModel<CompareStocksState> viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JPanel chooseStocksPanel = chooseStocksPanel();
        final JPanel pickTimeIntervalPanel = pickTimeIntervalPanel();
        final JTextArea comparisonSummary = comparisonSummaryComponent();
        final JButton compareButton = new JButton("Compare");

        this.add(chooseStocksPanel);
        this.add(pickTimeIntervalPanel);
        this.add(comparisonSummary);
        this.add(compareButton);

        compareButton.addActionListener(event -> getNewComparisonSummary());
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
        comparisonSummaryDisplay.setLineWrap(true);
        return comparisonSummaryDisplay;
    }

    private JPanel datePickerPanel() {
        final JPanel startDatePanel = new JPanel();
        startDatePanel.setLayout(new BoxLayout(startDatePanel, BoxLayout.Y_AXIS));

        final JLabel startDateLabel = new JLabel("Start date:");
        startDatePicker = new DatePicker();
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
        parentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, DATE_PANEL_HEIGHT));
        parentPanel.add(startDatePanel);
        parentPanel.add(endDatePanel);

        return parentPanel;
    }

    private void getNewComparisonSummary() {
        final String stock1Name = firstStockDropdown.getSelectedItem().toString();
        final String stock2Name = secondStockDropdown.getSelectedItem().toString();
        final Date start = Date.from(startDatePicker.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        final Date end = Date.from(endDatePicker.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

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
