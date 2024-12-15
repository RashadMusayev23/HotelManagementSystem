package model;

public class Hotel {
    private final int hotelId;
    private String hotelName;
    private String location;
    private String phoneNumber;
    private int roomCount;
    private String chainName;   // Added field
    private int starRating;     // Added field

    public Hotel(int hotelId, String hotelName, String location, String phoneNumber, int roomCount, String chainName, int starRating) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.roomCount = roomCount;
        this.chainName = chainName;
        this.starRating = starRating;
    }

    // Getters
    public int getHotelId() {
        return hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public String getChainName() {
        return chainName;
    }

    public int getStarRating() {
        return starRating;
    }

    // Setters
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }
}
