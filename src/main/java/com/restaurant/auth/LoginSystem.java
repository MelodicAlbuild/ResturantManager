package com.restaurant.auth;

import java.util.ArrayList;
import java.util.List;

public class LoginSystem {
    private List<Employee> employees;
    private Employee loggedInEmployee;

    public LoginSystem() {
        this.employees = new ArrayList<>();
        // Add some default employees for testing
        employees.add(new Employee("manager", "password123", "Manager"));
        employees.add(new Employee("waiter1", "orderfood", "Waiter"));
        employees.add(new Employee("chef1", "cooknow", "Chef"));
    }

    public boolean login(String username, String password) {
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                loggedInEmployee = employee;
                System.out.println("Login successful. Welcome, " + loggedInEmployee.getUsername() + " (" + loggedInEmployee.getRole() + ")");
                return true;
            }
        }
        System.out.println("Login failed. Invalid username or password.");
        return false;
    }

    // TODO: Implement logout functionality
    public void logout() {
    }

    public Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        System.out.println("Employee " + employee.getUsername() + " added.");
    }

    public void removeEmployee(String username) {
        employees.removeIf(employee -> employee.getUsername().equals(username));
        System.out.println("Employee " + username + " removed (if found).");
    }
}
