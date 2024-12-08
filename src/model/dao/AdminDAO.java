package model.dao;

import db.DBUtil;

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

    // A small data holder class for hotels
    public static class HotelInfo {
        private int hotelId;
        private String hotelName;
        public HotelInfo(int hotelId, String hotelName) {
            this.hotelId = hotelId;
            this.hotelName = hotelName;
        }
        public int getHotelId() { return hotelId; }
        public String getHotelName() { return hotelName; }
        @Override
        public String toString() { return hotelName; } // important for comboBox display
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

    // Add User Account (e.g. add a new Receptionist, Housekeeper)
    public void addUserAccount(String username, String password, String userType) throws SQLException {
        // Insert into User table and also into specific role table:
        // For simplicity assume user_id is auto increment and then insert into the role table
        String insertUser = "INSERT INTO User (user_id, username, password) VALUES (NULL, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    String roleTable = capitalize(userType);
                    String roleSQL = "INSERT INTO " + roleTable + " (user_id) VALUES (?)";
                    try (PreparedStatement rolePstmt = conn.prepareStatement(roleSQL)) {
                        rolePstmt.setInt(1, userId);
                        rolePstmt.executeUpdate();
                    }
                }
            }
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
}