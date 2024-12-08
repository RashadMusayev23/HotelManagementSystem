package model;

import db.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO {
    // Add New Booking
    public void addNewBooking(int bookingId, int guestId, int roomId, Date startDate, Date endDate) throws SQLException {
        String sql = "INSERT INTO Booking (booking_id, guest_id, room_id, start_date, end_date, payment_status, status) " +
                     "VALUES (?, ?, ?, ?, ?, 'Pending', 'Requested')";
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

    // View Available Rooms
    // For simplicity, we show all rooms that are not currently overlapping with a booking in the given date range.
    public List<String> viewAvailableRooms(Date startDate, Date endDate) throws SQLException {
        // This logic is simplified.
        String sql = 
            "SELECT room_name, room_type FROM Room r " +
            "WHERE r.status = 'Available' AND r.room_id NOT IN (" +
            " SELECT room_id FROM Booking b WHERE NOT (b.end_date < ? OR b.start_date > ?)" +
            ")";
        List<String> rooms = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    rooms.add("Room: " + rs.getString("room_name") + " (" + rs.getString("room_type") + ")");
                }
            }
        }
        return rooms;
    }

    // View My Bookings
    public List<String> viewMyBookings(int guestId) throws SQLException {
        String sql = "SELECT booking_id, room_id, start_date, end_date, status FROM Booking WHERE guest_id = ?";
        List<String> bookings = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, guestId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add("Booking ID: " + rs.getInt("booking_id") + ", Room ID: " + rs.getInt("room_id") +
                                 ", " + rs.getDate("start_date") + " to " + rs.getDate("end_date") +
                                 ", Status: " + rs.getString("status"));
                }
            }
        }
        return bookings;
    }

    // Cancel Booking
    // Guest can cancel if booking is still in Requested or Pending status and not checked-in.
    public void cancelBooking(int bookingId, int guestId) throws SQLException {
        String checkSql = "SELECT status FROM Booking WHERE booking_id = ? AND guest_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
            checkPstmt.setInt(1, bookingId);
            checkPstmt.setInt(2, guestId);
            try (ResultSet rs = checkPstmt.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");
                    if ("Requested".equalsIgnoreCase(status) || "Booked".equalsIgnoreCase(status)) {
                        // safe to cancel
                        String delSql = "DELETE FROM Booking WHERE booking_id = ?";
                        try (PreparedStatement delPstmt = conn.prepareStatement(delSql)) {
                            delPstmt.setInt(1, bookingId);
                            delPstmt.executeUpdate();
                            System.out.println("Your booking has been cancelled successfully.");
                        }
                    } else {
                        System.out.println("Cannot cancel this booking as it is already in progress or completed.");
                    }
                } else {
                    System.out.println("Booking not found or does not belong to you.");
                }
            }
        }
    }
}