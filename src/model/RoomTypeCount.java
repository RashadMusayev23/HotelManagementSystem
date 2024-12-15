package model;

public class RoomTypeCount {
    private String roomType;
    private int count;

    public RoomTypeCount(String roomType, int count) {
        this.roomType = roomType;
        this.count = count;
    }

    public String getRoomType() { return roomType; }
    public int getCount() { return count; }
}