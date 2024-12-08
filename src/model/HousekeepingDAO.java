package model;

import db.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HousekeepingDAO {
    // View Pending Housekeeping Tasks
    public List<String> viewPendingTasks(int housekeeperId) throws SQLException {
        List<String> tasks = new ArrayList<>();
        String sql = "SELECT housekeeping_id, room_id, schedule_date FROM Housekeeping WHERE housekeeper_id = ? AND cleaned_status = 'Pending'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, housekeeperId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add("Task ID: " + rs.getInt("housekeeping_id") +
                              ", Room ID: " + rs.getInt("room_id") +
                              ", Schedule: " + rs.getDate("schedule_date"));
                }
            }
        }
        return tasks;
    }

    // View Completed Housekeeping Tasks
    public List<String> viewCompletedTasks(int housekeeperId) throws SQLException {
        List<String> tasks = new ArrayList<>();
        String sql = "SELECT housekeeping_id, room_id, schedule_date FROM Housekeeping WHERE housekeeper_id = ? AND cleaned_status = 'Completed'";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, housekeeperId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add("Task ID: " + rs.getInt("housekeeping_id") +
                              ", Room ID: " + rs.getInt("room_id") +
                              ", Schedule: " + rs.getDate("schedule_date"));
                }
            }
        }
        return tasks;
    }

    // Update Task Status to Completed
    public void updateTaskStatus(int housekeepingId) throws SQLException {
        String sql = "UPDATE Housekeeping SET cleaned_status = 'Completed' WHERE housekeeping_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, housekeepingId);
            pstmt.executeUpdate();
        }
    }

    // View My Cleaning Schedule (same as viewing pending tasks but might show all tasks)
    public List<String> viewMyCleaningSchedule(int housekeeperId) throws SQLException {
        List<String> tasks = new ArrayList<>();
        String sql = "SELECT housekeeping_id, room_id, cleaned_status, schedule_date FROM Housekeeping WHERE housekeeper_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, housekeeperId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add("Task ID: " + rs.getInt("housekeeping_id") +
                              ", Room ID: " + rs.getInt("room_id") +
                              ", Status: " + rs.getString("cleaned_status") +
                              ", Date: " + rs.getDate("schedule_date"));
                }
            }
        }
        return tasks;
    }
}