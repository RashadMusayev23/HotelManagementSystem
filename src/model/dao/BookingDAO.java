package model.dao;

import db.DBUtil;
import model.Booking;

import java.sql.*;

public class BookingDAO {

    public void addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO Booking (booking_id, guest_id, room_id, start_date, end_date, payment_status, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, booking.getBookingId());
            pstmt.setInt(2, booking.getGuestId());
            pstmt.setInt(3, booking.getRoomId());
            pstmt.setDate(4, booking.getStartDate());
            pstmt.setDate(5, booking.getEndDate());
            pstmt.setString(6, booking.getPaymentStatus());
            pstmt.setString(7, booking.getStatus());

            pstmt.executeUpdate();
        }
    }

    public void modifyBookingDates(int bookingId, Date newStart, Date newEnd) throws SQLException {
        String sql = "UPDATE Booking SET start_date = ?, end_date = ? WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, newStart);
            pstmt.setDate(2, newEnd);
            pstmt.setInt(3, bookingId);

            pstmt.executeUpdate();
        }
    }

    public void deleteBooking(int bookingId) throws SQLException {
        String sql = "DELETE FROM Booking WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();
        }
    }

    public void checkIn(int bookingId) throws SQLException {
        String sql = "UPDATE Booking SET status = 'Checked In' WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();
        }
    }

    public void checkOut(int bookingId) throws SQLException {
        String sql = "UPDATE Booking SET status = 'Checked Out' WHERE booking_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();
        }
    }
}