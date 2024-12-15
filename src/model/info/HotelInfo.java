package model.info;

public class HotelInfo {
    private int hotelId;
    private String hotelName;

    public HotelInfo(int hotelId, String hotelName) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
    }

    public int getHotelId() {
        return hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    @Override
    public String toString() {
        return hotelName;
    }
}