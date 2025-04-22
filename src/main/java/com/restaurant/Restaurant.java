package com.restaurant;

import com.restaurant.auth.LoginSystem;
import com.restaurant.gui.RestaurantGUI;
import com.restaurant.menu.MenuManagement;
import com.restaurant.order.OrderManagement;
import com.restaurant.table.TableManagement;

import javax.swing.SwingUtilities;

public class Restaurant {

    private LoginSystem loginSystem;
    private TableManagement tableManagement;
    private MenuManagement menuManagement;
    private OrderManagement orderManagement;
    private RestaurantGUI gui;

    public Restaurant(int numberOfTables) {
        this.loginSystem = new LoginSystem();
        this.tableManagement = new TableManagement(numberOfTables);
        this.menuManagement = new MenuManagement();
        this.orderManagement = new OrderManagement();
        this.gui = new RestaurantGUI(loginSystem, tableManagement, menuManagement, orderManagement);
    }

    public void start() {
        loginSystem.addEmployee(new com.restaurant.auth.Employee("manager", "manager", "Manager"));

        SwingUtilities.invokeLater(() -> gui.run());

        System.out.println("Restaurant Management System started.");
    }

    public static void main(String[] args) {
        // Configure the number of tables when the application starts
        int numberOfTables = 15; // You can change this value

        Restaurant restaurant = new Restaurant(numberOfTables);
        restaurant.start();
    }
}