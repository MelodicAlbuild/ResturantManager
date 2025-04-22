package com.restaurant.auth;

public class Employee {
    private String username;
    private String password;
    private String role; // e.g., "Manager", "Waiter", "Chef"

    public Employee(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
