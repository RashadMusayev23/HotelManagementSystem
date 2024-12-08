package model.dao;

import db.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    // Assume a room is "available" if it does not have any Booking that overlaps with the given date range.
    // This is a simplified query. In reality, you'd refine this logic.

    public List<Integer> getAvailableRooms(Date startDate, Date endDate) throws SQLException {
        String sql = 
            "SELECT room_id FROM Room r " +
            "WHERE room_id NOT IN (" +
            " SELECT room_id FROM Booking b " +
            " WHERE NOT (b.end_date < ? OR b.start_date > ?)" +
            ")";
        // The above condition means: a booking conflicts if it does not lie completely before the start_date (b.end_date < start_date)
        // or completely after the end_date (b.start_date > end_date).
        // If we can't find a conflicting booking, the room is available.

        List<Integer> availableRooms = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    availableRooms.add(rs.getInt("room_id"));
                }
            }
        }

        return availableRooms;
    }
}