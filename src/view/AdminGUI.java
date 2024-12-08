package view;

import model.AdminDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdminGUI extends JFrame {
    private JButton addRoomButton, deleteRoomButton, manageRoomButton, addUserButton, viewUsersButton;
    private JButton revenueReportButton, viewAllBookingsButton, viewAllHousekeepingButton, viewMostBookedButton;
    private JButton viewAllEmployeesButton, cancelBookingNoPaymentButton, exitButton;

    private AdminDAO adminDAO;

    public AdminGUI() {
        super("Administrator Menu");
        adminDAO = new AdminDAO();
        setLayout(new GridLayout(12,1,10,10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addRoomButton = new JButton("Add Room");
        deleteRoomButton = new JButton("Delete Room");
        manageRoomButton = new JButton("Manage Room Status");
        addUserButton = new JButton("Add User Account");
        viewUsersButton = new JButton("View User Accounts");
        revenueReportButton = new JButton("Generate Revenue Report");
        viewAllBookingsButton = new JButton("View All Booking Records");
        viewAllHousekeepingButton = new JButton("View All Housekeeping Records");
        viewMostBookedButton = new JButton("View Most Booked Room Types");
        viewAllEmployeesButton = new JButton("View All Employees with Their Role");
        cancelBookingNoPaymentButton = new JButton("Cancel Booking if No Payment");
        exitButton = new JButton("Exit");

        add(addRoomButton);
        add(deleteRoomButton);
        add(manageRoomButton);
        add(addUserButton);
        add(viewUsersButton);
        add(revenueReportButton);
        add(viewAllBookingsButton);
        add(viewAllHousekeepingButton);
        add(viewMostBookedButton);
        add(viewAllEmployeesButton);
        add(cancelBookingNoPaymentButton);
        add(exitButton);

        addRoomButton.addActionListener(e -> addRoom());
        deleteRoomButton.addActionListener(e -> deleteRoom());
        manageRoomButton.addActionListener(e -> manageRoomStatus());
        addUserButton.addActionListener(e -> addUserAccount());
        viewUsersButton.addActionListener(e -> viewUserAccounts());
        revenueReportButton.addActionListener(e -> adminDAO.generateRevenueReport());
        viewAllBookingsButton.addActionListener(e -> viewAllBookingRecords());
        viewAllHousekeepingButton.addActionListener(e -> viewAllHousekeepingRecords());
        viewMostBookedButton.addActionListener(e -> adminDAO.viewMostBookedRoomTypes());
        viewAllEmployeesButton.addActionListener(e -> viewAllEmployees());
        cancelBookingNoPaymentButton.addActionListener(e -> cancelBookingIfNoPayment());
        exitButton.addActionListener(e -> dispose());

        pack();
    }

    private void addRoom() {
        try {
            String roomName = JOptionPane.showInputDialog(this, "Enter Room Name:");
            if (roomName == null) return;
            String roomType = JOptionPane.showInputDialog(this, "Enter Room Type:");
            if (roomType == null) return;
            String capStr = JOptionPane.showInputDialog(this, "Enter Max Capacity:");
            if (capStr == null) return;
            int cap = Integer.parseInt(capStr);
            String status = JOptionPane.showInputDialog(this, "Enter Status (e.g. 'Available'):");
            if (status == null) return;
            String hotelIdStr = JOptionPane.showInputDialog(this, "Enter Hotel ID:");
            if (hotelIdStr == null) return;
            int hId = Integer.parseInt(hotelIdStr);

            adminDAO.addRoom(roomName, roomType, cap, status, hId);
            JOptionPane.showMessageDialog(this, "Room added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRoom() {
        try {
            String roomName = JOptionPane.showInputDialog(this, "Enter Room Name to delete:");
            if (roomName == null) return;
            adminDAO.deleteRoomByName(roomName);
            JOptionPane.showMessageDialog(this, "Room deleted if possible.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manageRoomStatus() {
        try {
            String roomName = JOptionPane.showInputDialog(this, "Enter Room Name:");
            if (roomName == null) return;
            String newStatus = JOptionPane.showInputDialog(this, "Enter new Status:");
            if (newStatus == null) return;
            adminDAO.manageRoomStatus(roomName, newStatus);
            JOptionPane.showMessageDialog(this, "Room status updated.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addUserAccount() {
        try {
            String uname = JOptionPane.showInputDialog(this, "Enter Username:");
            if (uname == null) return;
            String pass = JOptionPane.showInputDialog(this, "Enter Password:");
            if (pass == null) return;
            String uType = JOptionPane.showInputDialog(this, "Enter User Type (Administrator, Receptionist, Housekeeper, Guest):");
            if (uType == null) return;
            adminDAO.addUserAccount(uname, pass, uType);
            JOptionPane.showMessageDialog(this, "User account added.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewUserAccounts() {
        try {
            List<String> users = adminDAO.viewUserAccounts();
            if (users.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No users found.");
            } else {
                StringBuilder sb = new StringBuilder("User Accounts:\n");
                for (String u : users) sb.append(u).append("\n");
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAllBookingRecords() {
        try {
            // Since method prints directly, we can capture output or just rely on console.
            // Better to modify DAO to return a list. For brevity, we trust the DAO prints to console.
            // Let's modify the DAO approach: for simplicity, let's just trust it prints to console.
            // We'll just call the method and show a message that it's done.
            // In a real app, you'd return a list and display it.
            System.out.println("All Booking Records:");
            adminDAO.viewAllBookingRecords();
            JOptionPane.showMessageDialog(this, "Check console for booking records. (Enhance DAO for GUI display)");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAllHousekeepingRecords() {
        try {
            System.out.println("All Housekeeping Records:");
            adminDAO.viewAllHousekeepingRecords();
            JOptionPane.showMessageDialog(this, "Check console for housekeeping records. (Enhance DAO for GUI display)");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAllEmployees() {
        try {
            System.out.println("All Employees with Role:");
            adminDAO.viewAllEmployeesWithRole();
            JOptionPane.showMessageDialog(this, "Check console for employee listing. (Enhance DAO for GUI display)");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelBookingIfNoPayment() {
        try {
            String bidStr = JOptionPane.showInputDialog(this, "Enter Booking ID:");
            if (bidStr == null) return;
            int bkid = Integer.parseInt(bidStr);
            adminDAO.cancelBookingIfNoPayment(bkid);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}