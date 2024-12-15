package model;

public class Employee {
    private int userId;
    private String username;
    private String role;

    public Employee(int userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
}