package model.info;

public class UserInfo {
    private int userId;
    private String username;
    private String role;

    public UserInfo(int userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
}