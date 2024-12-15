package model;

import java.sql.Date;

public class Booking {
    private int bookingId;
    private int guestId;
    private int roomId;
    private Date startDate;
    private Date endDate;
    private String paymentStatus;
    private String status;

    public Booking(int bookingId, int guestId, int roomId, Date startDate, Date endDate, String paymentStatus, String status) {
        this.bookingId = bookingId;
        this.guestId = guestId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentStatus = paymentStatus;
        this.status = status;
    }

    // Getters
    public int getBookingId() {
        return bookingId;
    }

    public int getGuestId() {
        return guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + ", Room ID: " + roomId + ", Guest ID: " + guestId +
                ", Start: " + startDate + ", End: " + endDate + ", Status: " + status;
    }
}