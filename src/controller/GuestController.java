package controller;

import model.BookingInfo;
import model.GuestInfo;
import model.RoomInfo;
import model.dao.GuestDAO;
import view.guest.GuestAddBookingView;
import view.guest.GuestCancelBookingView;
import view.guest.GuestViewAvailableRoomsView;
import view.guest.GuestViewMyBookingsView;

import javax.swing.*;
import java.util.List;

public class GuestController {
    private GuestDAO guestDAO;

    public GuestController(GuestDAO guestDAO) {
        this.guestDAO = guestDAO;
    }

    public void showAddBookingDialog(JFrame parent) {
        GuestAddBookingView dialog = new GuestAddBookingView(parent);
        try {
            // Load rooms
            List<RoomInfo> rooms = guestDAO.getAllRooms();
            if (rooms.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "No rooms available to book.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            dialog.setRooms(rooms.toArray());

            // Load guests
            List<GuestInfo> guests = guestDAO.getAllGuests();
            if (guests.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "No guests found. Please create a guest first.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            dialog.setGuests(guests.toArray());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dialog.getAddButton().addActionListener(e -> {
            try {
                GuestInfo selectedGuest = (GuestInfo) dialog.getSelectedGuest();
                RoomInfo selectedRoom = (RoomInfo) dialog.getSelectedRoom();
                java.sql.Date startDate = dialog.getSelectedStartDate();
                java.sql.Date endDate = dialog.getSelectedEndDate();

                if (startDate.after(endDate)) {
                    JOptionPane.showMessageDialog(dialog, "End date must be after start date.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                guestDAO.addNewBooking(selectedGuest.getUserId(), selectedRoom.getRoomId(), startDate, endDate);
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
                java.sql.Date startDate = dialog.getStartDate();
                java.sql.Date endDate = dialog.getEndDate();
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

        try {
            // Load guests and set them in the dialog
            List<GuestInfo> guests = guestDAO.getAllGuests();
            dialog.setGuests(guests.toArray());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading guests: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dialog.getViewButton().addActionListener(e -> {
            try {
                GuestInfo selectedGuest = dialog.getSelectedGuest();
                if (selectedGuest == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select a guest.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int guestId = selectedGuest.getUserId();
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

        try {
            List<GuestInfo> guests = guestDAO.getAllGuests();
            dialog.setGuests(guests.toArray());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading guests: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dialog.getLoadButton().addActionListener(e -> {
            try {
                GuestInfo selectedGuest = dialog.getSelectedGuest();
                if (selectedGuest == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select a guest.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int guestId = selectedGuest.getUserId();
                List<BookingInfo> bookings = guestDAO.getBookingsForGuest(guestId);
                dialog.setBookings(bookings.toArray());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error loading bookings: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.getCancelBookingButton().addActionListener(e -> {
            try {
                GuestInfo selectedGuest = dialog.getSelectedGuest();
                if (selectedGuest == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select a guest first.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int guestId = selectedGuest.getUserId();

                BookingInfo selectedBooking = dialog.getSelectedBooking();
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