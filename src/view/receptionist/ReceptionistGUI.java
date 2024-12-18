package view.receptionist;

import model.dao.ReceptionistDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class ReceptionistGUI extends JFrame {
    private JButton addBookingButton, modifyBookingButton, deleteBookingButton, viewBookingsButton;
    private JButton processPaymentButton, assignHkTaskButton, viewHkRecordsButton, exitButton;
    private ReceptionistDAO dao;

    public ReceptionistGUI() {
        super("Receptionist Menu");
        dao = new ReceptionistDAO();

        setLayout(new GridLayout(8, 1, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize buttons
        addBookingButton = createButton("Add New Booking", e -> addBooking());
        modifyBookingButton = createButton("Modify Booking", e -> modifyBooking());
        deleteBookingButton = createButton("Delete Booking", e -> deleteBooking());
        viewBookingsButton = createButton("View Bookings", e -> viewBookings());
        processPaymentButton = createButton("Process Payment", e -> processPayment());
        assignHkTaskButton = createButton("Assign Housekeeping Task", e -> assignHousekeepingTask());
        viewHkRecordsButton = createButton("View Housekeepers & Tasks", e -> viewHousekeepers());
        exitButton = createButton("Exit", e -> dispose());

        // Add buttons to GUI
        add(addBookingButton);
        add(modifyBookingButton);
        add(deleteBookingButton);
        add(viewBookingsButton);
        add(processPaymentButton);
        add(assignHkTaskButton);
        add(viewHkRecordsButton);
        add(exitButton);

        pack();
    }

    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    // Add New Booking
    private void addBooking() {
        try {
            int bookingId = promptForInt("Enter Booking ID:");
            int guestId = promptForInt("Enter Guest ID:");
            int roomId = promptForInt("Enter Room ID:");
            Date startDate = promptForDate("Select Check-in Date:");
            Date endDate = promptForDate("Select Check-out Date:");

            if (endDate.before(startDate)) {
                showError("Check-out date cannot be before check-in date.");
                return;
            }

            dao.addNewBooking(bookingId, guestId, roomId, startDate, endDate);
            showMessage("Booking successfully added!");
        } catch (Exception ex) {
            showError("Error while adding booking: " + ex.getMessage());
        }
    }

    // Modify Existing Booking
    private void modifyBooking() {
        try {
            int bookingId = promptForInt("Enter Booking ID:");
            Date newStart = promptForDate("Select New Start Date:");
            Date newEnd = promptForDate("Select New End Date:");

            if (newEnd.before(newStart)) {
                showError("End date cannot be before start date.");
                return;
            }

            dao.modifyBooking(bookingId, newStart, newEnd);
            showMessage("Booking modified successfully!");
        } catch (Exception ex) {
            showError("Error while modifying booking: " + ex.getMessage());
        }
    }

    // Delete a Booking
    private void deleteBooking() {
        try {
            int bookingId = promptForInt("Enter Booking ID to Delete:");
            dao.deleteBooking(bookingId);
            showMessage("Booking deleted successfully!");
        } catch (Exception ex) {
            showError("Error while deleting booking: " + ex.getMessage());
        }
    }

    // View Bookings
    private void viewBookings() {
        try {
            List<String[]> bookings = dao.viewBookings();
            if (bookings.isEmpty()) {
                showMessage("No bookings found.");
            } else {
                displayTable("Bookings", bookings, new String[]{"Booking ID", "Guest ID", "Room ID", "Start Date", "End Date", "Status"});
            }
        } catch (Exception ex) {
            showError("Error while viewing bookings: " + ex.getMessage());
        }
    }

    // Process Payment
    private void processPayment() {
        try {
            int bookingId = promptForInt("Enter Booking ID:");
            double amount = promptForDouble("Enter Payment Amount:");
            String[] methods = {"Credit Card", "Cash", "Debit Card"};
            String method = (String) JOptionPane.showInputDialog(this, "Select Payment Method:",
                    "Payment Method", JOptionPane.PLAIN_MESSAGE, null, methods, methods[0]);

            if (method == null) return;

            dao.processPayment(bookingId, amount, method);
            showMessage("Payment processed successfully!");
        } catch (Exception ex) {
            showError("Error while processing payment: " + ex.getMessage());
        }
    }

    // Assign Housekeeping Task
    private void assignHousekeepingTask() {
        try {
            int taskId = promptForInt("Enter Task ID:");
            int roomId = promptForInt("Enter Room ID:");
            Date scheduleDate = promptForDate("Select Schedule Date:");
            int housekeeperId = promptForInt("Enter Housekeeper ID:");

            dao.assignHousekeepingTask(taskId, roomId, scheduleDate, housekeeperId);
            showMessage("Housekeeping task assigned successfully!");
        } catch (Exception ex) {
            showError("Error while assigning housekeeping task: " + ex.getMessage());
        }
    }

    private void viewHousekeepers() {
        try {
            // Fetch housekeeper records
            List<String[]> housekeepers = dao.viewAllHousekeepers();

            if (housekeepers.isEmpty()) {
                showMessage("No housekeeper records found.");
            } else {
                // Define column names for JTable
                String[] columnNames = {"Housekeeper Name", "Task ID", "Room ID", "Status", "Scheduled Date"};

                // Display records in a JTable
                displayTable("Housekeepers & Their Tasks", housekeepers, columnNames);
            }
        } catch (Exception ex) {
            showError("Error while retrieving housekeeper records: " + ex.getMessage());
        }
    }

    private Date promptForDate(String title) {
        LocalDate today = LocalDate.now();
        JComboBox<Integer> yearBox = new JComboBox<>();
        JComboBox<Integer> monthBox = new JComboBox<>();
        JComboBox<Integer> dayBox = new JComboBox<>();

        for (int y = today.getYear(); y <= today.getYear() + 5; y++) yearBox.addItem(y);
        for (int m = 1; m <= 12; m++) monthBox.addItem(m);

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Year:"));
        panel.add(yearBox);
        panel.add(new JLabel("Month:"));
        panel.add(monthBox);
        panel.add(new JLabel("Day:"));
        panel.add(dayBox);

        yearBox.addActionListener(e -> refreshDays(yearBox, monthBox, dayBox));
        monthBox.addActionListener(e -> refreshDays(yearBox, monthBox, dayBox));
        refreshDays(yearBox, monthBox, dayBox);

        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int year = (Integer) yearBox.getSelectedItem();
            int month = (Integer) monthBox.getSelectedItem();
            int day = (Integer) dayBox.getSelectedItem();
            return Date.valueOf(LocalDate.of(year, month, day));
        }
        return null;
    }

    private void refreshDays(JComboBox<Integer> yearBox, JComboBox<Integer> monthBox, JComboBox<Integer> dayBox) {
        dayBox.removeAllItems();
        int year = (Integer) yearBox.getSelectedItem();
        int month = (Integer) monthBox.getSelectedItem();
        for (int d = 1; d <= YearMonth.of(year, month).lengthOfMonth(); d++) {
            dayBox.addItem(d);
        }
    }

    private void displayTable(String title, List<String[]> data, String[] columns) {
        JTable table = new JTable(data.toArray(new Object[0][0]), columns);
        JOptionPane.showMessageDialog(this, new JScrollPane(table), title, JOptionPane.INFORMATION_MESSAGE);
    }

    private int promptForInt(String message) {
        return Integer.parseInt(JOptionPane.showInputDialog(this, message));
    }

    private double promptForDouble(String message) {
        return Double.parseDouble(JOptionPane.showInputDialog(this, message));
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReceptionistGUI().setVisible(true));
    }
}
