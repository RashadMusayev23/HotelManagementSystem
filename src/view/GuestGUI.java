package view;

import model.GuestDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class GuestGUI extends JFrame {
    private JButton addBookingButton;
    private JButton viewAvailableRoomsButton;
    private JButton viewMyBookingsButton;
    private JButton cancelBookingButton;
    private JButton exitButton;

    private GuestDAO guestDAO;

    public GuestGUI() {
        super("Guest Menu");
        this.guestDAO = new GuestDAO();

        setLayout(new GridLayout(5, 1, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addBookingButton = new JButton("Add New Booking");
        viewAvailableRoomsButton = new JButton("View Available Rooms");
        viewMyBookingsButton = new JButton("View My Bookings");
        cancelBookingButton = new JButton("Cancel Booking");
        exitButton = new JButton("Exit");

        add(addBookingButton);
        add(viewAvailableRoomsButton);
        add(viewMyBookingsButton);
        add(cancelBookingButton);
        add(exitButton);

        addBookingButton.addActionListener(e -> addBooking());
        viewAvailableRoomsButton.addActionListener(e -> viewAvailableRooms());
        viewMyBookingsButton.addActionListener(e -> viewMyBookings());
        cancelBookingButton.addActionListener(e -> cancelBooking());
        exitButton.addActionListener(e -> dispose());

        pack();
    }

    private void addBooking() {
        try {
            String bookingIdStr = JOptionPane.showInputDialog(this, "Enter Booking ID:");
            if (bookingIdStr == null) return;
            int bookingId = Integer.parseInt(bookingIdStr);

            String guestIdStr = JOptionPane.showInputDialog(this, "Enter Guest ID:");
            if (guestIdStr == null) return;
            int guestId = Integer.parseInt(guestIdStr);

            String roomIdStr = JOptionPane.showInputDialog(this, "Enter Room ID:");
            if (roomIdStr == null) return;
            int roomId = Integer.parseInt(roomIdStr);

            String startDateStr = JOptionPane.showInputDialog(this, "Enter Start Date (YYYY-MM-DD):");
            if (startDateStr == null) return;
            Date startDate = Date.valueOf(startDateStr);

            String endDateStr = JOptionPane.showInputDialog(this, "Enter End Date (YYYY-MM-DD):");
            if (endDateStr == null) return;
            Date endDate = Date.valueOf(endDateStr);

            guestDAO.addNewBooking(bookingId, guestId, roomId, startDate, endDate);
            JOptionPane.showMessageDialog(this, "Booking requested successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAvailableRooms() {
        try {
            String startDateStr = JOptionPane.showInputDialog(this, "Enter Start Date (YYYY-MM-DD):");
            if (startDateStr == null) return;
            Date startDate = Date.valueOf(startDateStr);

            String endDateStr = JOptionPane.showInputDialog(this, "Enter End Date (YYYY-MM-DD):");
            if (endDateStr == null) return;
            Date endDate = Date.valueOf(endDateStr);

            List<String> rooms = guestDAO.viewAvailableRooms(startDate, endDate);
            if (rooms.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No available rooms for that period.");
            } else {
                StringBuilder sb = new StringBuilder("Available Rooms:\n");
                for (String r : rooms) {
                    sb.append(r).append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewMyBookings() {
        try {
            String guestIdStr = JOptionPane.showInputDialog(this, "Enter your Guest ID:");
            if (guestIdStr == null) return;
            int guestId = Integer.parseInt(guestIdStr);

            List<String> bookings = guestDAO.viewMyBookings(guestId);
            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(this, "You have no bookings.");
            } else {
                StringBuilder sb = new StringBuilder("Your Bookings:\n");
                for (String b : bookings) {
                    sb.append(b).append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid guest ID format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelBooking() {
        try {
            String guestIdStr = JOptionPane.showInputDialog(this, "Enter your Guest ID:");
            if (guestIdStr == null) return;
            int guestId = Integer.parseInt(guestIdStr);

            String bookingIdStr = JOptionPane.showInputDialog(this, "Enter Booking ID to cancel:");
            if (bookingIdStr == null) return;
            int bookingId = Integer.parseInt(bookingIdStr);

            guestDAO.cancelBooking(bookingId, guestId);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}