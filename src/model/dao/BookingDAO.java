package model.dao;

import db.DBUtil;
import model.Booking;

import java.sql.*;

public class BookingDAO {

    private static final String ADD_BOOKING_SQL = "INSERT INTO Booking (booking_id, guest_id, room_id, start_date, end_date, payment_status, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String MODIFY_BOOKING_DATES_SQL = "UPDATE Booking SET start_date = ?, end_date = ? WHERE booking_id = ?";
    private static final String DELETE_BOOKING_SQL = "DELETE FROM Booking WHERE booking_id = ?";
    private static final String CHECK_IN_SQL = "UPDATE Booking SET status = 'Checked In' WHERE booking_id = ?";
    private static final String CHECK_OUT_SQL = "UPDATE Booking SET status = 'Checked Out' WHERE booking_id = ?";

    public void addBooking(Booking booking) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(ADD_BOOKING_SQL)) {

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
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(MODIFY_BOOKING_DATES_SQL)) {

            pstmt.setDate(1, newStart);
            pstmt.setDate(2, newEnd);
            pstmt.setInt(3, bookingId);

            pstmt.executeUpdate();
        }
    }

    public void deleteBooking(int bookingId) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_BOOKING_SQL)) {
            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();
        }
    }

    public void checkIn(int bookingId) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CHECK_IN_SQL)) {
            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();
        }
    }

    public void checkOut(int bookingId) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CHECK_OUT_SQL)) {
            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();
        }
    }
}