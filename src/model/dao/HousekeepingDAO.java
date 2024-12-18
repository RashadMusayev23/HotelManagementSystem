package model.dao;

import db.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HousekeepingDAO {

    // Query to view pending tasks with receptionist info
    private static final String VIEW_PENDING_TASKS_SQL =
            "SELECT h.housekeeping_id, h.room_id, h.schedule_date, r.username AS assigned_by " +
                    "FROM Housekeeping h " +
                    "JOIN User r ON h.assigned_by = r.user_id " +
                    "WHERE h.housekeeper_id = ? AND h.cleaned_status = 'Pending'";

    // Query to view completed tasks
    private static final String VIEW_COMPLETED_TASKS_SQL =
            "SELECT housekeeping_id, room_id, schedule_date FROM Housekeeping " +
                    "WHERE housekeeper_id = ? AND cleaned_status = 'Completed'";

    // Query to update task status to 'Completed'
    private static final String UPDATE_TASK_STATUS_SQL =
            "UPDATE Housekeeping SET cleaned_status = 'Completed' WHERE housekeeping_id = ? AND cleaned_status = 'Pending'";

    // Query to view full cleaning schedule with status
    private static final String VIEW_SCHEDULE_SQL =
            "SELECT h.housekeeping_id, h.room_id, h.schedule_date, h.cleaned_status, r.username AS assigned_by " +
                    "FROM Housekeeping h " +
                    "JOIN User r ON h.assigned_by = r.user_id " +
                    "WHERE h.housekeeper_id = ?";

    // Query to view available rooms
    private static final String VIEW_AVAILABLE_ROOMS_SQL =
            "SELECT room_id, room_name, status FROM Room WHERE status = 'Available'";

    public List<String[]> getPendingTasks(int housekeeperId) throws SQLException {
        return fetchTasksWithReceptionist(VIEW_PENDING_TASKS_SQL, housekeeperId);
    }

    public List<Integer> getHousekeeperIds() throws SQLException {
        List<Integer> housekeeperIds = new ArrayList<>();
        String sql = "SELECT user_id FROM Housekeeper";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                housekeeperIds.add(rs.getInt("user_id"));
            }
        }
        return housekeeperIds;
    }


    public List<String[]> getCompletedTasks(int housekeeperId) throws SQLException {
        return fetchSimpleTasks(VIEW_COMPLETED_TASKS_SQL, housekeeperId);
    }

    public void updateTaskToCompleted(int taskId) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_TASK_STATUS_SQL)) {
            pstmt.setInt(1, taskId);
            int updated = pstmt.executeUpdate();
            if (updated == 0) {
                throw new SQLException("Task either does not exist or is already marked as completed.");
            }
        }
    }

    public List<String[]> getCleaningSchedule(int housekeeperId) throws SQLException {
        return fetchTasksWithReceptionist(VIEW_SCHEDULE_SQL, housekeeperId);
    }

    public List<String[]> getAvailableRooms() throws SQLException {
        List<String[]> rooms = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(VIEW_AVAILABLE_ROOMS_SQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                rooms.add(new String[]{
                        String.valueOf(rs.getInt("room_id")),
                        rs.getString("room_name"),
                        rs.getString("status")
                });
            }
        }
        return rooms;
    }

    // Helper to fetch tasks with assigned receptionist details
    private List<String[]> fetchTasksWithReceptionist(String sql, int housekeeperId) throws SQLException {
        List<String[]> tasks = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, housekeeperId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String assignedBy = "N/A"; // Default value in case the column does not exist
                    try {
                        assignedBy = rs.getString("assigned_by"); // Attempt to fetch assigned_by
                    } catch (SQLException e) {
                        // If assigned_by does not exist, skip it
                    }

                    tasks.add(new String[]{
                            String.valueOf(rs.getInt("housekeeping_id")),
                            String.valueOf(rs.getInt("room_id")),
                            rs.getDate("schedule_date").toString(),
                            assignedBy,
                            rs.getString("cleaned_status") == null ? "Pending" : rs.getString("cleaned_status")
                    });
                }
            }
        }
        return tasks;
    }


    // Helper to fetch simple task details
    private List<String[]> fetchSimpleTasks(String sql, int housekeeperId) throws SQLException {
        List<String[]> tasks = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, housekeeperId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(new String[]{
                            String.valueOf(rs.getInt("housekeeping_id")),
                            String.valueOf(rs.getInt("room_id")),
                            rs.getDate("schedule_date").toString()
                    });
                }
            }
        }
        return tasks;
    }
}
