package com.mycompany.inventorycontrolsystem.model;

import java.time.LocalDateTime;

/**
 * Model – maps to the `users` table.
 */
public class User {

    private int userId;
    private int roleId;
    private String username;
    private String passwordHash;
    private String fullName;
    private String email;
    private String phone;
    private boolean active;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    public User() {}

    public User(int userId, int roleId, String username, String fullName,
                String email, boolean active) {
        this.userId   = userId;
        this.roleId   = roleId;
        this.username = username;
        this.fullName = fullName;
        this.email    = email;
        this.active   = active;
    }
    public int getUserId()                      { return userId; }
    public void setUserId(int userId)           { this.userId = userId; }

    public int getRoleId()                      { return roleId; }
    public void setRoleId(int roleId)           { this.roleId = roleId; }

    public String getUsername()                 { return username; }
    public void setUsername(String username)    { this.username = username; }

    public String getPasswordHash()             { return passwordHash; }
    public void setPasswordHash(String h)       { this.passwordHash = h; }

    public String getFullName()                 { return fullName; }
    public void setFullName(String fullName)    { this.fullName = fullName; }

    public String getEmail()                    { return email; }
    public void setEmail(String email)          { this.email = email; }

    public String getPhone()                    { return phone; }
    public void setPhone(String phone)          { this.phone = phone; }

    public boolean isActive()                   { return active; }
    public void setActive(boolean active)       { this.active = active; }

    public LocalDateTime getLastLogin()         { return lastLogin; }
    public void setLastLogin(LocalDateTime t)   { this.lastLogin = t; }

    public LocalDateTime getCreatedAt()         { return createdAt; }
    public void setCreatedAt(LocalDateTime t)   { this.createdAt = t; }

    @Override
    public String toString() {
        return "User{id=" + userId + ", username='" + username + "', role=" + roleId + "}";
    }
}
