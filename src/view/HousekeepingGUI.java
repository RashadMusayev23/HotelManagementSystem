package view;

import model.HousekeepingDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class HousekeepingGUI extends JFrame {
    private JButton viewPendingButton, viewCompletedButton, updateTaskButton, viewScheduleButton, exitButton;
    private HousekeepingDAO dao;

    public HousekeepingGUI() {
        super("Housekeeping Menu");
        dao = new HousekeepingDAO();
        setLayout(new GridLayout(5,1,10,10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        viewPendingButton = new JButton("View Pending Housekeeping Tasks");
        viewCompletedButton = new JButton("View Completed Housekeeping Tasks");
        updateTaskButton = new JButton("Update Task Status to Completed");
        viewScheduleButton = new JButton("View My Cleaning Schedule");
        exitButton = new JButton("Exit");

        add(viewPendingButton);
        add(viewCompletedButton);
        add(updateTaskButton);
        add(viewScheduleButton);
        add(exitButton);

        viewPendingButton.addActionListener(e -> viewPendingTasks());
        viewCompletedButton.addActionListener(e -> viewCompletedTasks());
        updateTaskButton.addActionListener(e -> updateTaskStatus());
        viewScheduleButton.addActionListener(e -> viewSchedule());
        exitButton.addActionListener(e -> dispose());

        pack();
    }

    private void viewPendingTasks() {
        try {
            int hkId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter your Housekeeper ID:"));
            List<String> tasks = dao.viewPendingTasks(hkId);
            showListInDialog("Pending Tasks", tasks);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewCompletedTasks() {
        try {
            int hkId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter your Housekeeper ID:"));
            List<String> tasks = dao.viewCompletedTasks(hkId);
            showListInDialog("Completed Tasks", tasks);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTaskStatus() {
        try {
            int taskId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Housekeeping Task ID:"));
            dao.updateTaskStatus(taskId);
            JOptionPane.showMessageDialog(this, "Task updated to Completed.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewSchedule() {
        try {
            int hkId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter your Housekeeper ID:"));
            List<String> tasks = dao.viewMyCleaningSchedule(hkId);
            showListInDialog("My Cleaning Schedule", tasks);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showListInDialog(String title, List<String> list) {
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No records found.");
        } else {
            StringBuilder sb = new StringBuilder(title + ":\n");
            for (String s : list) sb.append(s).append("\n");
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }
}