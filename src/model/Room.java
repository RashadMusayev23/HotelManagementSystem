package model;

public class Room {
    private String roomName;
    private String roomType;
    private int maxCapacity;
    private String status;
    private int hotelId;

    public Room(String roomName, String roomType, int maxCapacity, String status, int hotelId) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.maxCapacity = maxCapacity;
        this.status = status;
        this.hotelId = hotelId;
    }

    public String getRoomName() { return roomName; }
    public String getRoomType() { return roomType; }
    public int getMaxCapacity() { return maxCapacity; }
    public String getStatus() { return status; }
    public int getHotelId() { return hotelId; }
}