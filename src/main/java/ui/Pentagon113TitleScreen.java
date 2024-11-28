import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class Pentagon113TitleScreen {
    // TODO: Fix the StyleErrors
    private JFrame frame;

    public Pentagon113TitleScreen() {
        createUI();
    }

    private void createUI() {
        // Create the frame
        frame = new JFrame("Pentagon113 Title Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Welcome to Pentagon113", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Calendar Input 1
        JLabel date1Label = new JLabel("Select Date 1:");
        JPanel date1Panel = createDateSelector();
        inputPanel.add(date1Label);
        inputPanel.add(date1Panel);

        // Calendar Input 2
        JLabel date2Label = new JLabel("Select Date 2:");
        JPanel date2Panel = createDateSelector();
        inputPanel.add(date2Label);
        inputPanel.add(date2Panel);

        frame.add(inputPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton preloadButton = new JButton("Preload Data");
        JButton resetButton = new JButton("Reset");

        buttonPanel.add(preloadButton);
        buttonPanel.add(resetButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        preloadButton.addActionListener(e -> {
            String date1 = getSelectedDate(date1Panel);
            String date2 = getSelectedDate(date2Panel);

            if (date1 == null || date2 == null) {
                JOptionPane.showMessageDialog(frame, "Please select valid dates!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Preloading data for Pentagon113 with inputs:\n" +
                                "Date 1: " + date1 + "\n" +
                                "Date 2: " + date2,
                        "Data Preloading", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        resetButton.addActionListener(e -> {
            resetDateSelector(date1Panel);
            resetDateSelector(date2Panel);
        });

        // Show the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createDateSelector() {
        JPanel dateSelector = new JPanel();
        dateSelector.setLayout(new GridLayout(1, 3, 5, 5));

        // Day ComboBox
        JComboBox<Integer> dayBox = new JComboBox<>();
        populateDays(dayBox, 2020, 1);

        // Month ComboBox
        JComboBox<Integer> monthBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            monthBox.addItem(i);
        }

        // Year ComboBox
        JComboBox<Integer> yearBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2020; i <= currentYear; i++) {
            yearBox.addItem(i);
        }

        dateSelector.add(dayBox);
        dateSelector.add(monthBox);
        dateSelector.add(yearBox);

        // Add listeners to update dayBox based on month and year
        yearBox.addActionListener(e -> updateDays(dayBox, (int) yearBox.getSelectedItem(), (int) monthBox.getSelectedItem()));
        monthBox.addActionListener(e -> updateDays(dayBox, (int) yearBox.getSelectedItem(), (int) monthBox.getSelectedItem()));

        // Store components as client properties for later retrieval
        dateSelector.putClientProperty("day", dayBox);
        dateSelector.putClientProperty("month", monthBox);
        dateSelector.putClientProperty("year", yearBox);

        return dateSelector;
    }

    private void populateDays(JComboBox<Integer> dayBox, int year, int month) {
        dayBox.removeAllItems();
        int daysInMonth = getDaysInMonth(year, month);
        Calendar today = Calendar.getInstance();

        for (int i = 1; i <= daysInMonth; i++) {
            if (year == today.get(Calendar.YEAR) && month == (today.get(Calendar.MONTH) + 1) && i >= today.get(Calendar.DAY_OF_MONTH)) {
                break;
            }
            dayBox.addItem(i);
        }
    }

    private void updateDays(JComboBox<Integer> dayBox, int year, int month) {
        populateDays(dayBox, year, month);
    }

    private int getDaysInMonth(int year, int month) {
        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            return 31;
        }
    }

    private String getSelectedDate(JPanel dateSelector) {
        JComboBox<Integer> dayBox = (JComboBox<Integer>) dateSelector.getClientProperty("day");
        JComboBox<Integer> monthBox = (JComboBox<Integer>) dateSelector.getClientProperty("month");
        JComboBox<Integer> yearBox = (JComboBox<Integer>) dateSelector.getClientProperty("year");

        if (dayBox == null || monthBox == null || yearBox == null || dayBox.getSelectedItem() == null) {
            return null;
        }

        int day = (int) dayBox.getSelectedItem();
        int month = (int) monthBox.getSelectedItem();
        int year = (int) yearBox.getSelectedItem();

        return String.format("%04d-%02d-%02d", year, month, day);
    }

    private void resetDateSelector(JPanel dateSelector) {
        JComboBox<Integer> dayBox = (JComboBox<Integer>) dateSelector.getClientProperty("day");
        JComboBox<Integer> monthBox = (JComboBox<Integer>) dateSelector.getClientProperty("month");
        JComboBox<Integer> yearBox = (JComboBox<Integer>) dateSelector.getClientProperty("year");

        if (dayBox != null) populateDays(dayBox, 2020, 1);
        if (monthBox != null) monthBox.setSelectedIndex(0);
        if (yearBox != null) yearBox.setSelectedIndex(0);
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static void main(String[] args) {
        new Pentagon113TitleScreen();
    }
}
