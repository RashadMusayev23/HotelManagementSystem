package controller;

import model.Room;
import model.dao.AdminDAO;
import model.info.HotelInfo;
import view.admin.AdminAddRoomView;
import view.admin.AdminManageRoomView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private AdminDAO adminDAO;

    public AdminController(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    // Show Add Room dialog
    public void showAddRoomDialog(JFrame parent) {
        AdminAddRoomView dialog = new AdminAddRoomView(parent);
        try {
            // Populate hotel comboBox from DB
            List<HotelInfo> hotels = adminDAO.getHotels();
            if (hotels.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "No hotels available to associate with the room.", "Warning", JOptionPane.WARNING_MESSAGE);
                return; // Cannot add room without a hotel
            }
            dialog.setHotels(hotels.toArray());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading hotels: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add button action
        dialog.getAddButton().addActionListener(e -> {
            String roomName = dialog.getRoomName();
            if (roomName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Room Name is required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String roomType = dialog.getRoomType();
            int capacity = dialog.getMaxCapacity();
            String status = dialog.getStatus();
            Object selectedHotel = dialog.getSelectedHotel();
            if (selectedHotel == null) {
                JOptionPane.showMessageDialog(dialog, "Please select a Hotel.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int hotelId = ((HotelInfo) selectedHotel).getHotelId();

            // Attempt to add room
            try {
                adminDAO.addRoom(roomName, roomType, capacity, status, hotelId);
                JOptionPane.showMessageDialog(dialog, "Room added successfully!");
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel button action
        dialog.getCancelButton().addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public void showDeleteRoomDialog(JFrame parent) {
        AdminManageRoomView dialog = new AdminManageRoomView(parent, "Delete Room", false); // Pass false to hide status

        try {
            // Load all rooms into the drop-down
            List<Room> rooms = adminDAO.getAllRooms();
            dialog.setRooms(rooms.toArray());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading rooms: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dialog.getConfirmButton().addActionListener(e -> {
            try {
                Room selectedRoom = dialog.getSelectedRoom();
                if (selectedRoom == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select a room.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                adminDAO.deleteRoom(selectedRoom.getRoomId());
                JOptionPane.showMessageDialog(dialog, "Room deleted successfully!");
                dialog.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Deletion Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.getCancelButton().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    // MANAGE ROOM STATUS FUNCTIONALITY
    public void showManageRoomStatusDialog(JFrame parent) {
        AdminManageRoomView dialog = new AdminManageRoomView(parent, "Manage Room Status", true);

        try {
            // Load all rooms into the drop-down
            List<Room> rooms = adminDAO.getAllRooms();
            dialog.setRooms(rooms.toArray());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading rooms: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dialog.getConfirmButton().addActionListener(e -> {
            try {
                Room selectedRoom = dialog.getSelectedRoom();
                String newStatus = dialog.getSelectedStatus();
                if (selectedRoom == null || newStatus == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select a room and status.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Perform status update
                adminDAO.updateRoomStatus(selectedRoom.getRoomId(), newStatus);
                JOptionPane.showMessageDialog(dialog, "Room status updated successfully!");
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.getCancelButton().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }
}