package controller;

import view.MenuView;
import model.BookingDAO;
import model.RoomDAO;
import model.Booking;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MenuController {
    private MenuView view;
    private BookingDAO bookingDAO;
    private RoomDAO roomDAO;

    public MenuController(MenuView view, BookingDAO bookingDAO, RoomDAO roomDAO) {
        this.view = view;
        this.bookingDAO = bookingDAO;
        this.roomDAO = roomDAO;

        this.view.addAddBookingListener(new AddBookingListener());
        this.view.addModifyBookingListener(new ModifyBookingListener());
        this.view.addDeleteBookingListener(new DeleteBookingListener());
        this.view.addViewAvailabilityListener(new ViewAvailabilityListener());
        this.view.addCheckInListener(new CheckInListener());
        this.view.addCheckOutListener(new CheckOutListener());
        this.view.addExitListener(e -> System.exit(0));
    }

    class AddBookingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String bookingIdStr = JOptionPane.showInputDialog(view, "Enter Booking ID:");
                if (bookingIdStr == null) return;
                int bookingId = Integer.parseInt(bookingIdStr);

                String guestIdStr = JOptionPane.showInputDialog(view, "Enter Guest ID:");
                if (guestIdStr == null) return;
                int guestId = Integer.parseInt(guestIdStr);

                String roomIdStr = JOptionPane.showInputDialog(view, "Enter Room ID:");
                if (roomIdStr == null) return;
                int roomId = Integer.parseInt(roomIdStr);

                String startDateStr = JOptionPane.showInputDialog(view, "Enter Start Date (YYYY-MM-DD):");
                if (startDateStr == null) return;
                Date startDate = Date.valueOf(startDateStr);

                String endDateStr = JOptionPane.showInputDialog(view, "Enter End Date (YYYY-MM-DD):");
                if (endDateStr == null) return;
                Date endDate = Date.valueOf(endDateStr);

                // For simplicity, assume payment_status = "Pending", status = "Booked"
                Booking booking = new Booking(bookingId, guestId, roomId, startDate, endDate, "Pending", "Booked");
                bookingDAO.addBooking(booking);

                JOptionPane.showMessageDialog(view, "Booking added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid number format. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ModifyBookingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String bookingIdStr = JOptionPane.showInputDialog(view, "Enter Booking ID to modify:");
                if (bookingIdStr == null) return;
                int bookingId = Integer.parseInt(bookingIdStr);

                String newStartStr = JOptionPane.showInputDialog(view, "Enter new Start Date (YYYY-MM-DD):");
                if (newStartStr == null) return;
                Date newStart = Date.valueOf(newStartStr);

                String newEndStr = JOptionPane.showInputDialog(view, "Enter new End Date (YYYY-MM-DD):");
                if (newEndStr == null) return;
                Date newEnd = Date.valueOf(newEndStr);

                bookingDAO.modifyBookingDates(bookingId, newStart, newEnd);
                JOptionPane.showMessageDialog(view, "Booking modified successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid number format. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class DeleteBookingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String bookingIdStr = JOptionPane.showInputDialog(view, "Enter Booking ID to delete:");
                if (bookingIdStr == null) return;
                int bookingId = Integer.parseInt(bookingIdStr);

                bookingDAO.deleteBooking(bookingId);
                JOptionPane.showMessageDialog(view, "Booking deleted successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid number format. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ViewAvailabilityListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String startStr = JOptionPane.showInputDialog(view, "Enter Start Date (YYYY-MM-DD):");
                if (startStr == null) return;
                String endStr = JOptionPane.showInputDialog(view, "Enter End Date (YYYY-MM-DD):");
                if (endStr == null) return;

                Date startDate = Date.valueOf(startStr);
                Date endDate = Date.valueOf(endStr);

                List<Integer> availableRooms = roomDAO.getAvailableRooms(startDate, endDate);
                if (availableRooms.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "No rooms are available in that date range.");
                } else {
                    JOptionPane.showMessageDialog(view, "Available rooms: " + availableRooms.toString());
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class CheckInListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String bookingIdStr = JOptionPane.showInputDialog(view, "Enter Booking ID to check in:");
                if (bookingIdStr == null) return;
                int bookingId = Integer.parseInt(bookingIdStr);

                bookingDAO.checkIn(bookingId);
                JOptionPane.showMessageDialog(view, "Check-in successful!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid number format. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class CheckOutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String bookingIdStr = JOptionPane.showInputDialog(view, "Enter Booking ID to check out:");
                if (bookingIdStr == null) return;
                int bookingId = Integer.parseInt(bookingIdStr);

                bookingDAO.checkOut(bookingId);
                JOptionPane.showMessageDialog(view, "Check-out successful!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid number format. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}