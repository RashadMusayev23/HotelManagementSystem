package view;

import model.ReceptionistDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReceptionistGUI extends JFrame {
    private JButton addBookingButton, modifyBookingButton, deleteBookingButton, viewBookingsButton;
    private JButton processPaymentButton, assignHkTaskButton, viewHkRecordsButton, exitButton;
    private ReceptionistDAO dao;

    public ReceptionistGUI() {
        super("Receptionist Menu");
        dao = new ReceptionistDAO();
        setLayout(new GridLayout(8,1,10,10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        addBookingButton = new JButton("Add New Booking");
        modifyBookingButton = new JButton("Modify Booking");
        deleteBookingButton = new JButton("Delete Booking");
        viewBookingsButton = new JButton("View Bookings");
        processPaymentButton = new JButton("Process Payment");
        assignHkTaskButton = new JButton("Assign Housekeeping Task");
        viewHkRecordsButton = new JButton("View Housekeepers & Their Tasks");
        exitButton = new JButton("Exit");

        add(addBookingButton);
        add(modifyBookingButton);
        add(deleteBookingButton);
        add(viewBookingsButton);
        add(processPaymentButton);
        add(assignHkTaskButton);
        add(viewHkRecordsButton);
        add(exitButton);

        addBookingButton.addActionListener(e -> addBooking());
        modifyBookingButton.addActionListener(e -> modifyBooking());
        deleteBookingButton.addActionListener(e -> deleteBooking());
        viewBookingsButton.addActionListener(e -> viewBookings());
        processPaymentButton.addActionListener(e -> processPayment());
        assignHkTaskButton.addActionListener(e -> assignHousekeepingTask());
        viewHkRecordsButton.addActionListener(e -> viewHousekeepers());
        exitButton.addActionListener(e -> dispose());

        pack();
    }

    private void addBooking() {
        try {
            int bookingId = Integer.parseInt(JOptionPane.showInputDialog(this, "Booking ID:"));
            int guestId = Integer.parseInt(JOptionPane.showInputDialog(this, "Guest ID:"));
            int roomId = Integer.parseInt(JOptionPane.showInputDialog(this, "Room ID:"));
            Date sd = Date.valueOf(JOptionPane.showInputDialog(this, "Start Date (YYYY-MM-DD):"));
            Date ed = Date.valueOf(JOptionPane.showInputDialog(this, "End Date (YYYY-MM-DD):"));
            dao.addNewBooking(bookingId, guestId, roomId, sd, ed);
            JOptionPane.showMessageDialog(this, "Booking added!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyBooking() {
        try {
            int bookingId = Integer.parseInt(JOptionPane.showInputDialog(this, "Booking ID:"));
            Date sd = Date.valueOf(JOptionPane.showInputDialog(this, "New Start Date (YYYY-MM-DD):"));
            Date ed = Date.valueOf(JOptionPane.showInputDialog(this, "New End Date (YYYY-MM-DD):"));
            dao.modifyBooking(bookingId, sd, ed);
            JOptionPane.showMessageDialog(this, "Booking modified!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBooking() {
        try {
            int bookingId = Integer.parseInt(JOptionPane.showInputDialog(this, "Booking ID:"));
            dao.deleteBooking(bookingId);
            JOptionPane.showMessageDialog(this, "Booking deleted!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewBookings() {
        try {
            List<String> bookings = dao.viewBookings();
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No bookings found.");
            } else {
                StringBuilder sb = new StringBuilder("Bookings:\n");
                for (String b : bookings) sb.append(b).append("\n");
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void processPayment() {
        try {
            int bookingId = Integer.parseInt(JOptionPane.showInputDialog(this, "Booking ID:"));
            double amt = Double.parseDouble(JOptionPane.showInputDialog(this, "Amount:"));
            String method = JOptionPane.showInputDialog(this, "Payment Method:");
            dao.processPayment(bookingId, amt, method);
            JOptionPane.showMessageDialog(this, "Payment processed!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void assignHousekeepingTask() {
        try {
            int hkid = Integer.parseInt(JOptionPane.showInputDialog(this, "Housekeeping Task ID:"));
            int roomId = Integer.parseInt(JOptionPane.showInputDialog(this, "Room ID:"));
            Date schDate = Date.valueOf(JOptionPane.showInputDialog(this, "Schedule Date (YYYY-MM-DD):"));
            int hkUserId = Integer.parseInt(JOptionPane.showInputDialog(this, "Housekeeper ID:"));
            dao.assignHousekeepingTask(hkid, roomId, schDate, hkUserId);
            JOptionPane.showMessageDialog(this, "Task assigned!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewHousekeepers() {
        try {
            System.out.println("Housekeepers Records:");
            dao.viewAllHousekeepers();
            JOptionPane.showMessageDialog(this, "Check console for housekeeper records. (Enhance DAO for direct GUI display)");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}