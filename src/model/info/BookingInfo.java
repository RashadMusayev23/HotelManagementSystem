package model.info;
import java.sql.Date;

public class BookingInfo {
    private int bookingId;
    private int roomId;
    private Date startDate;
    private Date endDate;
    private String status;

    public BookingInfo(int bookingId, int roomId, Date startDate, Date endDate, String status) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getBookingId() { return bookingId; }
    public int getRoomId() { return roomId; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Booking #" + bookingId + " (Room " + roomId + ", " + startDate + " to " + endDate + ")";
    }
}