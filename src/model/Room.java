package model;

public class Room {
    private int roomId;         // Added ID for Room
    private String roomName;
    private String roomType;
    private int maxCapacity;
    private String status;
    private int hotelId;
    private double rate;        // Added field
    private double discount;    // Added field

    public Room(int roomId, String roomName, String roomType, int maxCapacity, String status, int hotelId, double rate, double discount) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.maxCapacity = maxCapacity;
        this.status = status;
        this.hotelId = hotelId;
        this.rate = rate;
        this.discount = discount;
    }

    // Getters
    public int getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getStatus() {
        return status;
    }

    public int getHotelId() {
        return hotelId;
    }

    public double getRate() {
        return rate;
    }

    public double getDiscount() {
        return discount;
    }

    // Setters
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}