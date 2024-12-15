package model.dao;

import db.DBUtil;
import model.Room;
import model.info.HotelInfo;
import model.info.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    // Get list of (hotel_id, hotel_name) to populate the comboBox
    public List<HotelInfo> getHotels() throws SQLException {
        List<HotelInfo> hotels = new ArrayList<>();
        String sql = "SELECT hotel_id, hotel_name FROM Hotel";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                hotels.add(new HotelInfo(rs.getInt("hotel_id"), rs.getString("hotel_name")));
            }
        }
        return hotels;
    }

    // Fetch all rooms
    public List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Room";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("room_id"),
                        rs.getString("room_name"),
                        rs.getString("room_type"),
                        rs.getInt("max_capacity"),
                        rs.getString("status"),
                        rs.getInt("hotel_id"),
                        rs.getDouble("rate"),
                        rs.getDouble("discount")
                ));
            }
        }
        return rooms;
    }

    public boolean hasBookings(int roomId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Booking WHERE room_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if there are bookings
                }
            }
        }
        return false;
    }

    public void deleteRoom(int roomId) throws SQLException {
        if (hasBookings(roomId)) {
            throw new SQLException("Cannot delete the room because it has associated bookings.");
        }
        String sql = "DELETE FROM Room WHERE room_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.executeUpdate();
        }
    }

    // Add Room
    public void addRoom(String roomName, String roomType, int maxCapacity, String status, int hotelId) throws SQLException {
        String sql = "INSERT INTO Room (room_id, room_name, room_type, max_capacity, status, hotel_id) VALUES (NULL, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, roomName);
            pstmt.setString(2, roomType);
            pstmt.setInt(3, maxCapacity);
            pstmt.setString(4, status);
            pstmt.setInt(5, hotelId);
            pstmt.executeUpdate();
        }
    }

    // Update room status
    public void updateRoomStatus(int roomId, String newStatus) throws SQLException {
        String sql = "UPDATE Room SET status = ? WHERE room_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, roomId);
            stmt.executeUpdate();
        }
    }

    // Delete Room
    public void deleteRoomByName(String roomName) throws SQLException {
        // Must be careful: Only delete rooms that are not booked or handle business logic as required.
        // For simplicity, just delete if no ongoing booking.
        String sql = "DELETE FROM Room WHERE room_name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, roomName);
            pstmt.executeUpdate();
        }
    }

    // Manage Room Status (e.g. mark under maintenance)
    public void manageRoomStatus(String roomName, String newStatus) throws SQLException {
        String sql = "UPDATE Room SET status = ? WHERE room_name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newStatus);
            pstmt.setString(2, roomName);
            pstmt.executeUpdate();
        }
    }

    // View User Accounts
    public List<String> viewUserAccounts() throws SQLException {
        List<String> users = new ArrayList<>();
        String sql = "SELECT u.user_id, u.username, " +
                     "CASE WHEN a.user_id IS NOT NULL THEN 'Administrator' " +
                     "     WHEN r.user_id IS NOT NULL THEN 'Receptionist' " +
                     "     WHEN h.user_id IS NOT NULL THEN 'Housekeeper' " +
                     "     WHEN g.user_id IS NOT NULL THEN 'Guest' " +
                     "END AS role " +
                     "FROM User u " +
                     "LEFT JOIN Administrator a ON u.user_id = a.user_id " +
                     "LEFT JOIN Receptionist r ON u.user_id = r.user_id " +
                     "LEFT JOIN Housekeeper h ON u.user_id = h.user_id " +
                     "LEFT JOIN Guest g ON u.user_id = g.user_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                users.add("ID: " + rs.getInt("user_id") + ", Username: " + rs.getString("username") + ", Role: " + rs.getString("role"));
            }
        }
        return users;
    }

    // Generate Revenue Report (Stub: implement your own logic)
    public void generateRevenueReport() {
        // Placeholder: query Payment table and sum amounts.
        // Just print a placeholder for now.
        System.out.println("Revenue Report: [Stub - Implement logic]");
    }

    // View All Booking Records
    public void viewAllBookingRecords() throws SQLException {
        String sql = "SELECT * FROM Booking";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Booking ID: " + rs.getInt("booking_id") + ", Guest ID: " + rs.getInt("guest_id") +
                                   ", Room ID: " + rs.getInt("room_id") + ", Status: " + rs.getString("status"));
            }
        }
    }

    // View All Housekeeping Records
    public void viewAllHousekeepingRecords() throws SQLException {
        String sql = "SELECT * FROM Housekeeping";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Housekeeping ID: " + rs.getInt("housekeeping_id") + 
                                   ", Room ID: " + rs.getInt("room_id") +
                                   ", Status: " + rs.getString("cleaned_status") +
                                   ", Schedule: " + rs.getDate("schedule_date") +
                                   ", Housekeeper ID: " + rs.getInt("housekeeper_id"));
            }
        }
    }

    // View Most Booked Room Types (Stub)
    public void viewMostBookedRoomTypes() {
        System.out.println("Most Booked Room Types: [Stub - Implement logic with GROUP BY room_type, COUNT(*)]");
    }

    // View All Employees with Their Role
    public void viewAllEmployeesWithRole() throws SQLException {
        // We consider Administrator, Receptionist, Housekeeper as employees
        String sql = "SELECT u.username, 'Administrator' as role FROM Administrator a JOIN User u ON a.user_id = u.user_id " +
                     "UNION " +
                     "SELECT u.username, 'Receptionist' FROM Receptionist r JOIN User u ON r.user_id = u.user_id " +
                     "UNION " +
                     "SELECT u.username, 'Housekeeper' FROM Housekeeper h JOIN User u ON h.user_id = u.user_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Username: " + rs.getString("username") + ", Role: " + rs.getString("role"));
            }
        }
    }

    // Cancel a booking if no payment made:
    public void cancelBookingIfNoPayment(int bookingId) throws SQLException {
        // Check if no payment made:
        String checkSql = "SELECT * FROM Payment WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
            checkPstmt.setInt(1, bookingId);
            try (ResultSet rs = checkPstmt.executeQuery()) {
                if (!rs.next()) {
                    // No payment found, can delete booking
                    String deleteSql = "DELETE FROM Booking WHERE booking_id = ?";
                    try (PreparedStatement delPstmt = conn.prepareStatement(deleteSql)) {
                        delPstmt.setInt(1, bookingId);
                        delPstmt.executeUpdate();
                        System.out.println("Booking cancelled successfully.");
                    }
                } else {
                    System.out.println("Cannot cancel booking: Payment already made.");
                }
            }
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    public void addUserAccount(String username, String password, String role) throws SQLException {
        String insertUserSQL = "INSERT INTO User (username, password) VALUES (?, ?)";
        String getUserIdSQL = "SELECT LAST_INSERT_ID()";
        String insertRoleSQL = "";

        switch (role) {
            case "Administrator":
                insertRoleSQL = "INSERT INTO Administrator (user_id) VALUES (?)";
                break;
            case "Receptionist":
                insertRoleSQL = "INSERT INTO Receptionist (user_id) VALUES (?)";
                break;
            case "Housekeeper":
                insertRoleSQL = "INSERT INTO Housekeeper (user_id) VALUES (?)";
                break;
            case "Guest":
                insertRoleSQL = "INSERT INTO Guest (user_id) VALUES (?)";
                break;
            default:
                throw new SQLException("Invalid role selected.");
        }

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            // Insert into User table
            int userId;
            try (PreparedStatement userStmt = conn.prepareStatement(insertUserSQL)) {
                userStmt.setString(1, username);
                userStmt.setString(2, password);
                userStmt.executeUpdate();
            }

            // Get the last inserted user ID
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(getUserIdSQL)) {
                if (rs.next()) {
                    userId = rs.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve User ID.");
                }
            }

            // Insert into the appropriate role table
            try (PreparedStatement roleStmt = conn.prepareStatement(insertRoleSQL)) {
                roleStmt.setInt(1, userId);
                roleStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException ex) {
            throw new SQLException("Error adding user account: " + ex.getMessage());
        }
    }

    public List<UserInfo> getAllUserAccounts() throws SQLException {
        List<UserInfo> users = new ArrayList<>();
        String sql = "SELECT u.user_id, u.username, " +
                "CASE " +
                "WHEN a.user_id IS NOT NULL THEN 'Administrator' " +
                "WHEN r.user_id IS NOT NULL THEN 'Receptionist' " +
                "WHEN h.user_id IS NOT NULL THEN 'Housekeeper' " +
                "WHEN g.user_id IS NOT NULL THEN 'Guest' " +
                "END AS role " +
                "FROM User u " +
                "LEFT JOIN Administrator a ON u.user_id = a.user_id " +
                "LEFT JOIN Receptionist r ON u.user_id = r.user_id " +
                "LEFT JOIN Housekeeper h ON u.user_id = h.user_id " +
                "LEFT JOIN Guest g ON u.user_id = g.user_id";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new UserInfo(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role")
                ));
            }
        }
        return users;
    }
}