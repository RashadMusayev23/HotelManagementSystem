package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HotelDAO {
    private String url;
    private String user;
    private String password;

    public HotelDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void addHotel(Hotel hotel) throws SQLException {
        // Sample INSERT SQL
        String sql = "INSERT INTO Hotel (hotel_id, hotel_name, location, phone_number, room_count) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, hotel.getHotelId());
            pstmt.setString(2, hotel.getHotelName());
            pstmt.setString(3, hotel.getLocation());
            pstmt.setString(4, hotel.getPhoneNumber());
            pstmt.setInt(5, hotel.getRoomCount());
            pstmt.executeUpdate();
        }
    }
}
