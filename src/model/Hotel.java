package model;

public class Hotel {
    private final int hotelId;
    private String hotelName;
    private String location;
    private String phoneNumber;
    private int roomCount;

    public Hotel(int hotelId, String hotelName, String location, String phoneNumber, int roomCount) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.roomCount = roomCount;
    }

    public int getHotelId() { return hotelId; }
    public String getHotelName() { return hotelName; }
    public String getLocation() { return location; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getRoomCount() { return roomCount; }

    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public void setLocation(String location) { this.location = location; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setRoomCount(int roomCount) { this.roomCount = roomCount; }
}
