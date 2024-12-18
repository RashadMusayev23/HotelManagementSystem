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
            int housekeeperId = promptForInt("Enter Your Housekeeper ID:");
            List<String[]> tasks = fetcher.fetch(housekeeperId);
            displayTable(title, tasks, new String[]{"Task ID", "Room ID", "Schedule Date", "Assigned By", "Status"});
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
