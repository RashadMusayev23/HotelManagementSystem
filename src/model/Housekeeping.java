package model;

import java.sql.Date;

public class Housekeeping {
    private int housekeepingId;
    private int roomId;
    private String cleanedStatus;
    private Date scheduleDate;
    private int housekeeperId;

    public Housekeeping(int housekeepingId, int roomId, String cleanedStatus, Date scheduleDate, int housekeeperId) {
        this.housekeepingId = housekeepingId;
        this.roomId = roomId;
        this.cleanedStatus = cleanedStatus;
        this.scheduleDate = scheduleDate;
        this.housekeeperId = housekeeperId;
    }

    public int getHousekeepingId() { return housekeepingId; }
    public int getRoomId() { return roomId; }
    public String getCleanedStatus() { return cleanedStatus; }
    public Date getScheduleDate() { return scheduleDate; }
    public int getHousekeeperId() { return housekeeperId; }
}