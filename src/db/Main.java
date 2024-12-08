package db;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hotel_management";
        String user = "root";
        String password = "mazeppa1881";

        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to database successful!");

            String query = "SELECT * FROM Hotel";
            try (
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)
            ) {
                while (rs.next()) {
                    int hotelId = rs.getInt("hotel_id");
                    String hotelName = rs.getString("hotel_name");
                    System.out.println("Hotel ID: " + hotelId + ", Name: " + hotelName);
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }
}