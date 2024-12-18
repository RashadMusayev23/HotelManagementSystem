package model.dao;

import db.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceptionistDAO {

    public void addNewBooking(int guestId, int roomId, Date startDate, Date endDate) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            // Insert the new booking into the Booking table
            String sql = "INSERT INTO Booking (guest_id, room_id, start_date, end_date, payment_status, status) " +
                    "VALUES (?, ?, ?, ?, 'Pending', 'Requested')";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, guestId);
                pstmt.setInt(2, roomId);
                pstmt.setDate(3, startDate);
                pstmt.setDate(4, endDate);
                pstmt.executeUpdate();
            }
        }
    }


    public List<Integer> getGuestIds() throws SQLException {
        List<Integer> guestIds = new ArrayList<>();
        String sql = "SELECT user_id FROM Guest";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                guestIds.add(rs.getInt("user_id"));
            }
        }
        return guestIds;
    }

    public List<Integer> getAvailableRoomIds() throws SQLException {
        List<Integer> roomIds = new ArrayList<>();
        String sql = "SELECT room_id FROM Room WHERE status = 'Available'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                roomIds.add(rs.getInt("room_id"));
            }
        }
        return roomIds;
    }


    // Fetch Room IDs
    public List<Integer> getRoomIds() throws SQLException {
        List<Integer> roomIds = new ArrayList<>();
        String sql = "SELECT room_id FROM Room WHERE status = 'Available'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                roomIds.add(rs.getInt("room_id"));
            }
        }
        return roomIds;
    }

    // Fetch Available Booking IDs
    public List<Integer> getAvailableBookingIds() throws SQLException {
        List<Integer> bookingIds = new ArrayList<>();
        String sql = "SELECT MAX(booking_id) + 1 AS next_id FROM Booking";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                bookingIds.add(rs.getInt("next_id"));
            }
        }
        return bookingIds;
    }


    // Modify existing booking dates
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

    // Delete a booking
    public void deleteBooking(int bookingId) throws SQLException {
        String sql = "DELETE FROM Booking WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();
        }
    }

    // View all bookings
    public List<String[]> viewBookings() throws SQLException {
        List<String[]> bookings = new ArrayList<>();
        String sql = "SELECT booking_id, guest_id, room_id, start_date, end_date, status FROM Booking";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                bookings.add(new String[]{
                        String.valueOf(rs.getInt("booking_id")),
                        String.valueOf(rs.getInt("guest_id")),
                        String.valueOf(rs.getInt("room_id")),
                        rs.getDate("start_date").toString(),
                        rs.getDate("end_date").toString(),
                        rs.getString("status")
                });
            }
        }
        return bookings;
    }

    // Process Payment
    public void processPayment(int bookingId, double amount, String method) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            // Check if booking exists and is unpaid
            String checkSql = "SELECT payment_status FROM Booking WHERE booking_id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, bookingId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Booking not found.");
                    if ("Paid".equals(rs.getString("payment_status"))) {
                        throw new SQLException("Payment has already been processed for this booking.");
                    }
                }
            }

            // Insert payment record
            String paymentSql = "INSERT INTO Payment (booking_id, amount, payment_date, payment_method) VALUES (?, ?, CURDATE(), ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(paymentSql)) {
                pstmt.setInt(1, bookingId);
                pstmt.setDouble(2, amount);
                pstmt.setString(3, method);
                pstmt.executeUpdate();
            }

            // Update booking payment status
            String updateSql = "UPDATE Booking SET payment_status = 'Paid' WHERE booking_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, bookingId);
                updateStmt.executeUpdate();
            }
        }
    }

    // Assign housekeeping task
    public void assignHousekeepingTask(int taskId, int roomId, Date scheduleDate, int housekeeperId) throws SQLException {
        String sql = "INSERT INTO Housekeeping (housekeeping_id, room_id, cleaned_status, schedule_date, housekeeper_id) " +
                "VALUES (?, ?, 'Pending', ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            pstmt.setInt(2, roomId);
            pstmt.setDate(3, scheduleDate);
            pstmt.setInt(4, housekeeperId);
            pstmt.executeUpdate();
        }
    }

    // View all housekeepers and their tasks
    public List<String[]> viewAllHousekeepers() throws SQLException {
        List<String[]> results = new ArrayList<>();
        String sql = "SELECT u.username, h.housekeeping_id, h.room_id, h.cleaned_status, h.schedule_date " +
                "FROM Housekeeping h JOIN User u ON h.housekeeper_id = u.user_id";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                results.add(new String[]{
                        rs.getString("username"),
                        String.valueOf(rs.getInt("housekeeping_id")),
                        String.valueOf(rs.getInt("room_id")),
                        rs.getString("cleaned_status"),
                        rs.getDate("schedule_date").toString()
                });
            }
        }
        return results;
    }

    // Helper method to check room availability
    private boolean isRoomAvailableForBooking(Connection conn, int roomId, Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM Booking WHERE room_id = ? AND NOT (end_date <= ? OR start_date >= ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            pstmt.setDate(2, startDate);
            pstmt.setDate(3, endDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt("cnt") == 0;
            }
        }
        return false;
    }
}
