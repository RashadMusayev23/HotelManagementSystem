package view.housekeeping;

import model.dao.HousekeepingDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class HousekeepingGUI extends JFrame {
    private JButton viewPendingButton, viewCompletedButton, updateTaskButton, viewScheduleButton, viewRoomAvailabilityButton, exitButton;
    private HousekeepingDAO dao;

    public HousekeepingGUI() {
        super("Housekeeping Menu");
        dao = new HousekeepingDAO();

        setLayout(new GridLayout(6, 1, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        viewPendingButton = createButton("View Pending Tasks", e -> viewPendingTasks());
        viewCompletedButton = createButton("View Completed Tasks", e -> viewCompletedTasks());
        updateTaskButton = createButton("Update Task Status", e -> updateTaskToCompleted());
        viewScheduleButton = createButton("View Cleaning Schedule", e -> viewSchedule());
        viewRoomAvailabilityButton = createButton("View Available Rooms", e -> viewAvailableRooms());
        exitButton = createButton("Exit", e -> dispose());

        add(viewPendingButton);
        add(viewCompletedButton);
        add(updateTaskButton);
        add(viewScheduleButton);
        add(viewRoomAvailabilityButton);
        add(exitButton);

        pack();
    }

    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void viewPendingTasks() {
        displayTasks("Pending Tasks", id -> dao.getPendingTasks(id));
    }

    private void viewCompletedTasks() {
        displayTasks("Completed Tasks", id -> dao.getCompletedTasks(id));
    }

    private void viewSchedule() {
        displayTasks("Cleaning Schedule", id -> dao.getCleaningSchedule(id));
    }

    private void updateTaskToCompleted() {
        try {
            int taskId = promptForInt("Enter Task ID to mark as Completed:");
            dao.updateTaskToCompleted(taskId);
            JOptionPane.showMessageDialog(this, "Task marked as completed.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAvailableRooms() {
        try {
            List<String[]> rooms = dao.getAvailableRooms();
            displayTable("Available Rooms", rooms, new String[]{"Room ID", "Room Name", "Status"});
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayTasks(String title, TaskFetcher fetcher) {
        try {
            // Fetch available Housekeeper IDs from the DAO
            List<Integer> housekeeperIds = dao.getHousekeeperIds();

            if (housekeeperIds.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No housekeepers available.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create dropdown for Housekeeper IDs
            JComboBox<Integer> housekeeperBox = new JComboBox<>(housekeeperIds.toArray(new Integer[0]));
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(new JLabel("Select Housekeeper ID:"));
            panel.add(housekeeperBox);

            int result = JOptionPane.showConfirmDialog(this, panel, "Housekeeper Selection", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                int housekeeperId = (Integer) housekeeperBox.getSelectedItem();
                List<String[]> tasks = fetcher.fetch(housekeeperId);

                // Display tasks in a table
                displayTable(title, tasks, new String[]{"Task ID", "Room ID", "Schedule Date", "Assigned By", "Status"});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void displayTable(String title, List<String[]> data, String[] columnNames) {
        JTable table = new JTable(new DefaultTableModel(data.toArray(new Object[][]{}), columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(this, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private int promptForInt(String message) {
        String input = JOptionPane.showInputDialog(this, message);
        if (input == null) throw new IllegalArgumentException("Input cancelled.");
        return Integer.parseInt(input.trim());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HousekeepingGUI().setVisible(true));
    }

    @FunctionalInterface
    interface TaskFetcher {
        List<String[]> fetch(int housekeeperId) throws SQLException;
    }
}