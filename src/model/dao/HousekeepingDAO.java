package model.dao;

import db.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HousekeepingDAO {

    // Define SQL queries as constants
    private static final String VIEW_PENDING_TASKS_SQL =
            "SELECT housekeeping_id, room_id, schedule_date FROM Housekeeping WHERE housekeeper_id = ? AND cleaned_status = 'Pending'";
    private static final String VIEW_COMPLETED_TASKS_SQL =
            "SELECT housekeeping_id, room_id, schedule_date FROM Housekeeping WHERE housekeeper_id = ? AND cleaned_status = 'Completed'";
    private static final String UPDATE_TASK_STATUS_SQL =
            "UPDATE Housekeeping SET cleaned_status = 'Completed' WHERE housekeeping_id = ?";
    private static final String VIEW_CLEANING_SCHEDULE_SQL =
            "SELECT housekeeping_id, room_id, cleaned_status, schedule_date FROM Housekeeping WHERE housekeeper_id = ?";

    // View Pending Housekeeping Tasks
    public List<String> viewPendingTasks(int housekeeperId) throws SQLException {
        return fetchTasks(VIEW_PENDING_TASKS_SQL, housekeeperId, false);
    }

    // View Completed Housekeeping Tasks
    public List<String> viewCompletedTasks(int housekeeperId) throws SQLException {
        return fetchTasks(VIEW_COMPLETED_TASKS_SQL, housekeeperId, false);
    }

    // View My Cleaning Schedule
    public List<String> viewMyCleaningSchedule(int housekeeperId) throws SQLException {
        return fetchTasks(VIEW_CLEANING_SCHEDULE_SQL, housekeeperId, true);
    }

    // Update Task Status to Completed
    public void updateTaskStatus(int housekeepingId) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_TASK_STATUS_SQL)) {
            pstmt.setInt(1, housekeepingId);
            pstmt.executeUpdate();
        }
    }

    // Private Helper Method to Fetch Tasks
    private List<String> fetchTasks(String sql, int housekeeperId, boolean includeStatus) throws SQLException {
        List<String> tasks = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, housekeeperId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String task = "Task ID: " + rs.getInt("housekeeping_id") +
                            ", Room ID: " + rs.getInt("room_id") +
                            ", Schedule: " + rs.getDate("schedule_date");
                    if (includeStatus) {
                        task += ", Status: " + rs.getString("cleaned_status");
                    }
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }
}