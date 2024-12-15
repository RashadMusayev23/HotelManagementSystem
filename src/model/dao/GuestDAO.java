package model.dao;

import db.DBUtil;
import model.BookingInfo;
import model.GuestInfo;
import model.RoomInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO {

    public List<GuestInfo> getAllGuests() throws SQLException {
        String sql = "SELECT u.user_id, u.username FROM User u " +
                "JOIN Guest g ON u.user_id = g.user_id";
        List<GuestInfo> guests = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                guests.add(new GuestInfo(rs.getInt("user_id"), rs.getString("username")));
            }
        }
        return guests;
    }

    // Add New Booking (Revised to not require booking_id if it's auto-increment)
    public void addNewBooking(int guestId, int roomId, Date startDate, Date endDate) throws SQLException {
        String sql = "INSERT INTO Booking (guest_id, room_id, start_date, end_date, payment_status, status) " +
                     "VALUES (?, ?, ?, ?, 'Pending', 'Requested')";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, guestId);
            pstmt.setInt(2, roomId);
            pstmt.setDate(3, startDate);
            pstmt.setDate(4, endDate);
            pstmt.executeUpdate();
        }
    }

    // Get Available Rooms as RoomInfo objects
    public List<RoomInfo> getAvailableRooms(java.sql.Date startDate, java.sql.Date endDate) throws SQLException {
        String sql = 
            "SELECT r.room_id, r.room_name, r.room_type, r.max_capacity, r.status " +
            "FROM Room r " +
            "WHERE r.status = 'Available' " +
            "AND r.room_id NOT IN (" +
            "   SELECT room_id FROM Booking b " +
            "   WHERE NOT (b.end_date < ? OR b.start_date > ?)" +
            ")";
        List<RoomInfo> availableRooms = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    RoomInfo room = new RoomInfo(
                        rs.getInt("room_id"),
                        rs.getString("room_name"),
                        rs.getString("room_type"),
                        rs.getInt("max_capacity"),
                        rs.getString("status")
                    );
                    availableRooms.add(room);
                }
            }
        }
        return availableRooms;
    }

    // Get Bookings for Guest as BookingInfo objects
    public List<BookingInfo> getBookingsForGuest(int guestId) throws SQLException {
        String sql = "SELECT booking_id, room_id, start_date, end_date, status FROM Booking WHERE guest_id = ?";
        List<BookingInfo> bookings = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, guestId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BookingInfo booking = new BookingInfo(
                        rs.getInt("booking_id"),
                        rs.getInt("room_id"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("status")
                    );
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }

    // Cancel Booking logic remains the same as before
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

    // Existing getAllRooms method:
    public List<RoomInfo> getAllRooms() throws SQLException {
        String sql = "SELECT room_id, room_name, room_type, max_capacity, status FROM Room";
        List<RoomInfo> allRooms = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                RoomInfo info = new RoomInfo(
                    rs.getInt("room_id"),
                    rs.getString("room_name"),
                    rs.getString("room_type"),
                    rs.getInt("max_capacity"),
                    rs.getString("status")
                );
                allRooms.add(info);
            }
        }
        return allRooms;
    }
}