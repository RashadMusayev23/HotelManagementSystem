package controller;

import model.*;
import model.dao.AdminDAO;
import model.info.HotelInfo;
import model.info.UserInfo;
import view.admin.*;

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
            int rate = dialog.getRate();
            int discount = dialog.getDiscount();
            String status = dialog.getStatus();
            Object selectedHotel = dialog.getSelectedHotel();
            if (selectedHotel == null) {
                JOptionPane.showMessageDialog(dialog, "Please select a Hotel.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int hotelId = ((HotelInfo) selectedHotel).getHotelId();

            // Attempt to add room
            try {
                adminDAO.addRoom(roomName, roomType, capacity, status, hotelId, rate, discount); // Add room to the database
                JOptionPane.showMessageDialog(parent, "Room added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                // Check for duplicate entry error
                if (ex.getErrorCode() == 1062) { // MySQL error code for duplicate entry
                    JOptionPane.showMessageDialog(parent,
                            "Error: A room with the name '" + roomName + "' already exists. Please choose a different name.",
                            "Duplicate Room Name",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parent,
                            "An unexpected error occurred: " + ex.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                }
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

    public void showAddUserAccountDialog(JFrame parent) {
        AdminAddUserView dialog = new AdminAddUserView(parent);

        dialog.getAddButton().addActionListener(e -> {
            try {
                String username = dialog.getUsername();
                String password = dialog.getPassword();
                String role = dialog.getSelectedRole();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Username and password cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                adminDAO.addUserAccount(username, password, role);
                JOptionPane.showMessageDialog(dialog, "User account created successfully!");
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.getCancelButton().addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public void showViewUserAccountsDialog(JFrame parent) {
        AdminViewUserAccountsView dialog = new AdminViewUserAccountsView(parent);

        try {
            List<UserInfo> users = adminDAO.getAllUserAccounts();
            dialog.updateUserTable(users);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading user accounts: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        dialog.getCloseButton().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public void showViewAllBookingRecords(JFrame parent) {
        AdminViewRecordsView dialog = new AdminViewRecordsView(parent, "View All Booking Records");

        try {
            List<Booking> bookings = adminDAO.getAllBookingRecords();
            dialog.updateTable(prepareBookingData(bookings), new String[]{"Booking ID", "Guest ID", "Room ID", "Start Date", "End Date", "Payment Status", "Status"});
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading booking records: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        dialog.setVisible(true);
    }

    public void showViewAllHousekeepingRecords(JFrame parent) {
        AdminViewRecordsView dialog = new AdminViewRecordsView(parent, "View All Housekeeping Records");

        try {
            List<Housekeeping> records = adminDAO.getAllHousekeepingRecords();
            dialog.updateTable(prepareHousekeepingData(records), new String[]{"Record ID", "Room ID", "Cleaned Status", "Schedule Date", "Housekeeper ID"});
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading housekeeping records: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        dialog.setVisible(true);
    }

    // Utility methods to prepare data for JTable
    private Object[][] prepareBookingData(List<Booking> bookings) {
        Object[][] data = new Object[bookings.size()][7];
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            data[i] = new Object[]{b.getBookingId(), b.getGuestId(), b.getRoomId(), b.getStartDate(), b.getEndDate(), b.getPaymentStatus(), b.getStatus()};
        }
        return data;
    }

    private Object[][] prepareHousekeepingData(List<Housekeeping> records) {
        Object[][] data = new Object[records.size()][5];
        for (int i = 0; i < records.size(); i++) {
            Housekeeping h = records.get(i);
            data[i] = new Object[]{h.getHousekeepingId(), h.getRoomId(), h.getCleanedStatus(), h.getScheduleDate(), h.getHousekeeperId()};
        }
        return data;
    }

    public void showMostBookedRoomTypes(JFrame parent) {
        AdminViewRecordsView dialog = new AdminViewRecordsView(parent, "Most Booked Room Types");

        try {
            List<RoomTypeCount> roomTypes = adminDAO.getMostBookedRoomTypes();
            dialog.updateTable(prepareRoomTypeData(roomTypes), new String[]{"Room Type", "Bookings"});
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading room types: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        dialog.setVisible(true);
    }

    public void showAllEmployeesWithRole(JFrame parent) {
        AdminViewRecordsView dialog = new AdminViewRecordsView(parent, "All Employees with Their Role");

        try {
            List<Employee> employees = adminDAO.getAllEmployeesWithRole();
            dialog.updateTable(prepareEmployeeData(employees), new String[]{"User ID", "Username", "Role"});
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading employees: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        dialog.setVisible(true);
    }

    public void showCancelBookingIfNoPaymentDialog(JFrame parent) {
        AdminCancelBookingView dialog = new AdminCancelBookingView(parent);

        try {
            List<Booking> pendingBookings = adminDAO.getBookingsWithNoPayment();
            dialog.setBookings(pendingBookings.toArray());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error loading pending bookings: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dialog.getCancelButton().addActionListener(e -> {
            try {
                Booking selectedBooking = dialog.getSelectedBooking();
                if (selectedBooking == null) {
                    JOptionPane.showMessageDialog(dialog, "Please select a booking to cancel.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                adminDAO.cancelBooking(selectedBooking.getBookingId());
                JOptionPane.showMessageDialog(dialog, "Booking cancelled successfully!");
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.getCloseButton().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    // Utility methods for table preparation
    private Object[][] prepareRoomTypeData(List<RoomTypeCount> roomTypes) {
        Object[][] data = new Object[roomTypes.size()][2];
        for (int i = 0; i < roomTypes.size(); i++) {
            RoomTypeCount r = roomTypes.get(i);
            data[i] = new Object[]{r.getRoomType(), r.getCount()};
        }
        return data;
    }

    private Object[][] prepareEmployeeData(List<Employee> employees) {
        Object[][] data = new Object[employees.size()][3];
        for (int i = 0; i < employees.size(); i++) {
            Employee e = employees.get(i);
            data[i] = new Object[]{e.getUserId(), e.getUsername(), e.getRole()};
        }
        return data;
    }

    public void showGenerateRevenueReport(JFrame parent) {
        AdminViewRecordsView dialog = new AdminViewRecordsView(parent, "Revenue Report");

        String[] options = {"By Hotel", "By Room Type"};
        String choice = (String) JOptionPane.showInputDialog(parent,
                "Generate Revenue Report:", "Choose Option",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == null) {
            return; // User canceled
        }

        try {
            List<RevenueReport> report;
            if (choice.equals("By Hotel")) {
                report = adminDAO.getRevenueByHotel();
                dialog.updateTable(prepareRevenueByHotelData(report), new String[]{"Hotel Name", "Total Revenue"});
            } else {
                report = adminDAO.getRevenueByRoomType();
                dialog.updateTable(prepareRevenueByRoomTypeData(report), new String[]{"Room Type", "Total Revenue"});
            }
            dialog.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Object[][] prepareRevenueByHotelData(List<RevenueReport> reports) {
        Object[][] data = new Object[reports.size()][2];
        for (int i = 0; i < reports.size(); i++) {
            data[i][0] = reports.get(i).getCategory();
            data[i][1] = reports.get(i).getTotalRevenue();
        }
        return data;
    }

    private Object[][] prepareRevenueByRoomTypeData(List<RevenueReport> reports) {
        return prepareRevenueByHotelData(reports); // Same logic applies
    }
}