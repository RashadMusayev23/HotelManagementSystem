package model.info;

public class RoomInfo {
    private int roomId;
    private String roomName;
    private String roomType;
    private int maxCapacity;
    private String status;

    public RoomInfo(int roomId, String roomName, String roomType, int maxCapacity, String status) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.maxCapacity = maxCapacity;
        this.status = status;
    }

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

    @Override
    public String toString() {
        return roomName + " (" + roomType + ")";
    }
}