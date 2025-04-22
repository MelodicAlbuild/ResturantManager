package com.restaurant.gui;

import com.restaurant.auth.LoginSystem;
import com.restaurant.menu.MenuManagement;
import com.restaurant.order.OrderManagement;
import com.restaurant.table.Table;
import com.restaurant.table.TableManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RestaurantGUI extends JFrame {

    private LoginSystem loginSystem;
    private TableManagement tableManagement;
    private MenuManagement menuManagement;
    private OrderManagement orderManagement;

    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JPanel loginPanel;
    private JPanel mainAppPanel;
    private JPanel tablePanel;
    private JPanel menuPanel;
    private JPanel orderPanel;

    private JLabel loginMessage;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public RestaurantGUI(LoginSystem loginSystem, TableManagement tableManagement, MenuManagement menuManagement, OrderManagement orderManagement) {
        this.loginSystem = loginSystem;
        this.tableManagement = tableManagement;
        this.menuManagement = menuManagement;
        this.orderManagement = orderManagement;

        setTitle("Restaurant Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        setupLoginPanel();
        setupMainAppPanel();
        setupTablePanel();
        setupMenuPanel();
        setupOrderPanel();

        mainPanel.add(loginPanel, "login");
        mainPanel.add(mainAppPanel, "mainApp");
        mainPanel.add(tablePanel, "tables");
        mainPanel.add(menuPanel, "menu");
        mainPanel.add(orderPanel, "orders");

        add(mainPanel);

        cardLayout.show(mainPanel, "login");
    }

    private void setupLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        loginMessage = new JLabel("Please log in:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(loginMessage, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (loginSystem.login(username, password)) {
                    cardLayout.show(mainPanel, "mainApp");
                } else {
                    loginMessage.setText("Login failed. Invalid username or password.");
                }
                passwordField.setText(""); // Clear password field
            }
        });
    }

    private void setupMainAppPanel() {
        mainAppPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton tablesButton = new JButton("Manage Tables");
        JButton menuButton = new JButton("Manage Menu");
        JButton ordersButton = new JButton("Manage Orders");
        JButton logoutButton = new JButton("Logout");

        mainAppPanel.add(tablesButton);
        mainAppPanel.add(menuButton);
        mainAppPanel.add(ordersButton);
        mainAppPanel.add(logoutButton);

        tablesButton.addActionListener(e -> cardLayout.show(mainPanel, "tables"));
        menuButton.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        ordersButton.addActionListener(e -> cardLayout.show(mainPanel, "orders"));
        logoutButton.addActionListener(e -> {
            loginSystem.logout();
            cardLayout.show(mainPanel, "login");
            loginMessage.setText("Please log in:");
        });
    }

    private void setupTablePanel() {
        tablePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Table Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tablePanel.add(titleLabel, BorderLayout.NORTH);

        JPanel tableListPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Grid layout for tables
        JScrollPane scrollPane = new JScrollPane(tableListPanel);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        List<Table> tables = tableManagement.getAllTables();
        for (Table table : tables) {
            JButton tableButton = new JButton("Table " + table.getTableNumber() + " (" + table.getStatus() + ")");
            tableButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO: Implement actions when a table button is clicked (e.g., view/edit table status)
                    JOptionPane.showMessageDialog(RestaurantGUI.this, "Table " + table.getTableNumber() + " selected.");
                }
            });
            tableListPanel.add(tableButton);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "mainApp"));
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupMenuPanel() {
        menuPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Menu Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel menuItemListPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JScrollPane scrollPane = new JScrollPane(menuItemListPanel);
        menuPanel.add(scrollPane, BorderLayout.CENTER);

        java.util.List<com.restaurant.menu.MenuItem> menuItems = menuManagement.getAllMenuItems();
        for (com.restaurant.menu.MenuItem item : menuItems) {
            JLabel itemLabel = new JLabel(item.toString());
            menuItemListPanel.add(itemLabel);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add Item");
        JButton editButton = new JButton("Edit Item");
        JButton deleteButton = new JButton("Delete Item");
        JButton backButton = new JButton("Back");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "mainApp"));
        menuPanel.add(buttonPanel, BorderLayout.SOUTH);

        // TODO: Implement action listeners for add, edit, and delete buttons to open dialogs for these operations.
    }

    private void setupOrderPanel() {
        orderPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Order Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        orderPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel placeholderLabel = new JLabel("Order Management Panel (Under Development)", SwingConstants.CENTER);
        orderPanel.add(placeholderLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton newOrderButton = new JButton("New Order");
        JButton viewOrderButton = new JButton("View Order");
        JButton backButton = new JButton("Back");
        buttonPanel.add(newOrderButton);
        buttonPanel.add(viewOrderButton);
        buttonPanel.add(backButton);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "mainApp"));
        orderPanel.add(buttonPanel, BorderLayout.SOUTH);

        // TODO: Implement action listeners for creating and viewing orders.
    }

    public void run() {
        setVisible(true);
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        TableManagement tableManagement = new TableManagement(10); // Default Value
        MenuManagement menuManagement = new MenuManagement();
        OrderManagement orderManagement = new OrderManagement();

        SwingUtilities.invokeLater(() -> new RestaurantGUI(loginSystem, tableManagement, menuManagement, orderManagement).run());
    }
}
