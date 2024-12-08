package controller;

import model.BookingInfo;
import model.dao.GuestDAO;
import model.RoomInfo;
import view.guest.GuestAddBookingView;
import view.guest.GuestCancelBookingView;
import view.guest.GuestViewAvailableRoomsView;
import view.guest.GuestViewMyBookingsView;

import javax.swing.*;
import java.sql.Date;
import java.util.List;

public class GuestController {
    private GuestDAO guestDAO;

    public GuestController(GuestDAO guestDAO) {
        this.guestDAO = guestDAO;
    }

    public void showAddBookingDialog(JFrame parent) {
        GuestAddBookingView dialog = new GuestAddBookingView(parent);
        try {
            // Fetch list of rooms to show in comboBox
            List<RoomInfo> rooms = guestDAO.getAllRooms();
            if (rooms.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "No rooms available to book.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            dialog.setRooms(rooms.toArray());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading rooms: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dialog.getAddButton().addActionListener(e -> {
            try {
                int guestId = dialog.getGuestId();
                RoomInfo selectedRoom = (RoomInfo) dialog.getSelectedRoom();
                Date startDate = dialog.getStartDate();
                Date endDate = dialog.getEndDate();
                
                if (startDate.after(endDate)) {
                    JOptionPane.showMessageDialog(dialog, "End date must be after start date.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                guestDAO.addNewBooking(guestId, selectedRoom.getRoomId(), startDate, endDate);
                JOptionPane.showMessageDialog(dialog, "Booking requested successfully!");
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.getCancelButton().addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public void showViewAvailableRoomsDialog(JFrame parent) {
        GuestViewAvailableRoomsView dialog = new GuestViewAvailableRoomsView(parent);

        dialog.getSearchButton().addActionListener(e -> {
            try {
                Date startDate = dialog.getStartDate();
                Date endDate = dialog.getEndDate();
                if (startDate.after(endDate)) {
                    JOptionPane.showMessageDialog(dialog, "End date must be after start date.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                List<RoomInfo> availableRooms = guestDAO.getAvailableRooms(startDate, endDate);
                dialog.updateRoomTable(availableRooms);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.getCloseButton().addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public void showViewMyBookingsDialog(JFrame parent) {
        GuestViewMyBookingsView dialog = new GuestViewMyBookingsView(parent);

        dialog.getViewButton().addActionListener(e -> {
            try {
                int guestId = dialog.getGuestId();
                List<BookingInfo> bookings = guestDAO.getBookingsForGuest(guestId);
                dialog.updateBookingTable(bookings);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.getCloseButton().addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public void showCancelBookingDialog(JFrame parent) {
        GuestCancelBookingView dialog = new GuestCancelBookingView(parent);

        dialog.getLoadButton().addActionListener(e -> {
            try {
                int guestId = dialog.getGuestId();
                List<BookingInfo> bookings = guestDAO.getBookingsForGuest(guestId);
                dialog.setBookings(bookings.toArray());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error loading bookings: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.getCancelBookingButton().addActionListener(e -> {
            try {
                int guestId = dialog.getGuestId();
                BookingInfo selectedBooking = (BookingInfo) dialog.getSelectedBooking();
                if (selectedBooking == null) {
                    JOptionPane.showMessageDialog(dialog, "Select a booking to cancel.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                guestDAO.cancelBooking(selectedBooking.getBookingId(), guestId);
                JOptionPane.showMessageDialog(dialog, "Booking cancelled successfully!");
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.getCloseButton().addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}