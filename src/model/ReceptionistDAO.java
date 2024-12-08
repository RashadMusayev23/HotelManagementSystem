package model;

import db.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceptionistDAO {
    // Add New Booking (similar to Guest but Receptionist can directly confirm or modify)
    public void addNewBooking(int bookingId, int guestId, int roomId, Date startDate, Date endDate) throws SQLException {
        String sql = "INSERT INTO Booking (booking_id, guest_id, room_id, start_date, end_date, payment_status, status) " +
                     "VALUES (?, ?, ?, ?, ?, 'Pending', 'Booked')";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            pstmt.setInt(2, guestId);
            pstmt.setInt(3, roomId);
            pstmt.setDate(4, startDate);
            pstmt.setDate(5, endDate);
            pstmt.executeUpdate();
        }
    }

    // Modify Booking (Dates)
    public void modifyBooking(int bookingId, Date newStart, Date newEnd) throws SQLException {
        String sql = "UPDATE Booking SET start_date = ?, end_date = ? WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, newStart);
            pstmt.setDate(2, newEnd);
            pstmt.setInt(3, bookingId);
            pstmt.executeUpdate();
        }
    }

    // Delete Booking
    public void deleteBooking(int bookingId) throws SQLException {
        String sql = "DELETE FROM Booking WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();
        }
    }

    // View Bookings
    public List<String> viewBookings() throws SQLException {
        List<String> bookings = new ArrayList<>();
        String sql = "SELECT booking_id, guest_id, room_id, status FROM Booking";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                bookings.add("Booking ID: " + rs.getInt("booking_id") +
                             ", Guest ID: " + rs.getInt("guest_id") +
                             ", Room ID: " + rs.getInt("room_id") +
                             ", Status: " + rs.getString("status"));
            }
        }
        return bookings;
    }

    // Process Payment (Mark a booking as paid)
    public void processPayment(int bookingId, double amount, String paymentMethod) throws SQLException {
        String insertPayment = "INSERT INTO Payment (payment_id, booking_id, amount, payment_date, payment_method) " +
                               "VALUES (NULL, ?, ?, CURDATE(), ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertPayment)) {
            pstmt.setInt(1, bookingId);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, paymentMethod);
            pstmt.executeUpdate();
            // Mark booking payment_status as Paid
            String updateBooking = "UPDATE Booking SET payment_status = 'Paid' WHERE booking_id = ?";
            try (PreparedStatement ubPstmt = conn.prepareStatement(updateBooking)) {
                ubPstmt.setInt(1, bookingId);
                ubPstmt.executeUpdate();
            }
        }
    }

    // Assign Housekeeping Task
    public void assignHousekeepingTask(int housekeepingId, int roomId, Date scheduleDate, int housekeeperId) throws SQLException {
        String sql = "INSERT INTO Housekeeping (housekeeping_id, room_id, cleaned_status, schedule_date, housekeeper_id) VALUES (?, ?, 'Pending', ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, housekeepingId);
            pstmt.setInt(2, roomId);
            pstmt.setDate(3, scheduleDate);
            pstmt.setInt(4, housekeeperId);
            pstmt.executeUpdate();
        }
    }

    // View All Housekeepers Records and Their Availability
    public void viewAllHousekeepers() throws SQLException {
        // Show housekeepers and their scheduled tasks
        String sql = "SELECT u.username, h.user_id FROM Housekeeper h JOIN User u ON h.user_id = u.user_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int housekeeperId = rs.getInt("user_id");
                System.out.println("Housekeeper: " + rs.getString("username") + " (ID: " + housekeeperId + ")");
                // Check their tasks
                String taskSql = "SELECT housekeeping_id, room_id, cleaned_status, schedule_date FROM Housekeeping WHERE housekeeper_id = ?";
                try (PreparedStatement tPstmt = conn.prepareStatement(taskSql)) {
                    tPstmt.setInt(1, housekeeperId);
                    try (ResultSet trs = tPstmt.executeQuery()) {
                        while (trs.next()) {
                            System.out.println("  Task: " + trs.getInt("housekeeping_id") +
                                               ", Room: " + trs.getInt("room_id") +
                                               ", Status: " + trs.getString("cleaned_status") +
                                               ", Date: " + trs.getDate("schedule_date"));
                        }
                    }
                }
            }
        }
    }
}