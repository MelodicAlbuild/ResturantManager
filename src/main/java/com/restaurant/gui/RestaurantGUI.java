package com.restaurant.gui;

import com.restaurant.auth.LoginSystem;
import com.restaurant.menu.MenuManagement;
import com.restaurant.order.Order;
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
    private JPanel tableListPanel;
    private JPanel menuPanel;
    private JPanel orderPanel;
    private JTextArea orderDetailsTextArea;

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

        tableListPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Grid layout for tables
        JScrollPane tableScrollPane = new JScrollPane(tableListPanel);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

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

    private void refreshTablePanel() {
        tableListPanel.removeAll();

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
    }

    private void setupMenuPanel() {
        menuPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Menu Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        menuPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel mainContentPanel = new JPanel(new GridLayout(1, 2)); // Split menu list and add to order
        menuPanel.add(mainContentPanel, BorderLayout.CENTER);

        // Left side: Menu Item List
        JPanel menuItemListPanel = new JPanel(new BorderLayout());
        DefaultListModel<com.restaurant.menu.MenuItem> menuItemListModel = new DefaultListModel<>();
        JList<com.restaurant.menu.MenuItem> menuItemList = new JList<>(menuItemListModel);
        JScrollPane menuScrollPane = new JScrollPane(menuItemList);
        menuItemListPanel.add(menuScrollPane, BorderLayout.CENTER);
        menuItemListPanel.add(new JLabel("Menu Items", SwingConstants.CENTER), BorderLayout.NORTH);
        mainContentPanel.add(menuItemListPanel);

        // Custom ListCellRenderer for multi-line description
        menuItemList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof com.restaurant.menu.MenuItem) {
                    com.restaurant.menu.MenuItem item = (com.restaurant.menu.MenuItem) value;
                    String text = "<html><b>" + item.getName() + "</b> ($" + String.format("%.2f", item.getPrice()) + ")<br>" +
                            "<div style='margin-left: 10px;'>" + item.getDescription() + " [" + item.getCategory() + "]</div></html>";
                    label.setText(text);
                }
                return label;
            }
        });

        // Right side: Add to Order
        JPanel addToOrderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(5);
        JButton addToOrderButton = new JButton("Add to Order");
        addToOrderPanel.add(quantityLabel);
        addToOrderPanel.add(quantityField);
        addToOrderPanel.add(addToOrderButton);
        mainContentPanel.add(addToOrderPanel);

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

        // Load menu items
        for (com.restaurant.menu.MenuItem item : menuManagement.getAllMenuItems()) {
            menuItemListModel.addElement(item);
        }

        // Functionality to add item to the selected order
        addToOrderButton.addActionListener(e -> {
            Order currentOrder = ((DefaultListModel<Order>) ((JList<Order>) ((JScrollPane) ((JPanel) ((JPanel) orderPanel.getComponent(1)).getComponent(0)).getComponent(1)).getViewport().getComponent(0)).getModel()).getElementAt(((JList<Order>) ((JScrollPane) ((JPanel) ((JPanel) orderPanel.getComponent(1)).getComponent(0)).getComponent(1)).getViewport().getComponent(0)).getSelectedIndex());
            com.restaurant.menu.MenuItem selectedMenuItem = menuItemList.getSelectedValue();
            String quantityText = quantityField.getText();
            if (currentOrder != null && selectedMenuItem != null && !quantityText.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityText);
                    if (quantity > 0) {
                        orderManagement.addItemToOrder(currentOrder, selectedMenuItem, quantity);
                        // Refresh the order details in the Order Panel
                        orderDetailsTextArea.setText(currentOrder.toString());
                    } else {
                        JOptionPane.showMessageDialog(RestaurantGUI.this, "Quantity must be greater than 0.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(RestaurantGUI.this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(RestaurantGUI.this, "Please select an order and a menu item, and enter a quantity.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
            quantityField.setText(""); // Clear quantity field
        });

        // Action listener for "Add Item" button (Menu Management)
        addButton.addActionListener(e -> {
            JTextField nameField = new JTextField(20);
            JTextField descriptionField = new JTextField(30);
            JTextField priceField = new JTextField(10);
            String[] categories = {"Appetizer", "Main Course", "Dessert", "Beverage"};
            JComboBox<String> categoryComboBox = new JComboBox<>(categories);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Price:"));
            panel.add(priceField);
            panel.add(new JLabel("Category:"));
            panel.add(categoryComboBox);

            int result = JOptionPane.showConfirmDialog(RestaurantGUI.this, panel, "Add New Menu Item", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    String description = descriptionField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    String category = (String) categoryComboBox.getSelectedItem();
                    if (!name.isEmpty()) {
                        com.restaurant.menu.MenuItem newItem = new com.restaurant.menu.MenuItem(name, description, price, category);
                        menuManagement.addMenuItem(newItem);
                        menuItemListModel.addElement(newItem);
                    } else {
                        JOptionPane.showMessageDialog(RestaurantGUI.this, "Menu item name cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(RestaurantGUI.this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action listener for "Edit Item" button (Menu Management)
        editButton.addActionListener(e -> {
            com.restaurant.menu.MenuItem selectedItem = menuItemList.getSelectedValue();
            if (selectedItem != null) {
                JTextField nameField = new JTextField(selectedItem.getName(), 20);
                JTextField descriptionField = new JTextField(selectedItem.getDescription(), 30);
                JTextField priceField = new JTextField(String.valueOf(selectedItem.getPrice()), 10);
                String[] categories = {"Appetizer", "Main Course", "Dessert", "Beverage"};
                JComboBox<String> categoryComboBox = new JComboBox<>(categories);
                categoryComboBox.setSelectedItem(selectedItem.getCategory());

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Description:"));
                panel.add(descriptionField);
                panel.add(new JLabel("Price:"));
                panel.add(priceField);
                panel.add(new JLabel("Category:"));
                panel.add(categoryComboBox);

                int result = JOptionPane.showConfirmDialog(RestaurantGUI.this, panel, "Edit Menu Item", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String oldName = selectedItem.getName();
                        String name = nameField.getText();
                        String description = descriptionField.getText();
                        double price = Double.parseDouble(priceField.getText());
                        String category = (String) categoryComboBox.getSelectedItem();
                        menuManagement.updateMenuItem(oldName, description, price, category);
                        // Refresh the list (simplistic approach - reload all)
                        menuItemListModel.removeAllElements();
                        for (com.restaurant.menu.MenuItem item : menuManagement.getAllMenuItems()) {
                            menuItemListModel.addElement(item);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(RestaurantGUI.this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(RestaurantGUI.this, "Please select a menu item to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Action listener for "Delete Item" button (Menu Management)
        deleteButton.addActionListener(e -> {
            com.restaurant.menu.MenuItem selectedItem = menuItemList.getSelectedValue();
            if (selectedItem != null) {
                int confirmResult = JOptionPane.showConfirmDialog(RestaurantGUI.this,
                        "Are you sure you want to delete '" + selectedItem.getName() + "'?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                    menuManagement.removeMenuItem(selectedItem.getName());
                    menuItemListModel.removeElement(selectedItem);
                }
            } else {
                JOptionPane.showMessageDialog(RestaurantGUI.this, "Please select a menu item to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void setupOrderPanel() {
        orderPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Order Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        orderPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel mainOrderArea = new JPanel(new GridLayout(1, 2)); // Split into active orders and order details/creation
        orderPanel.add(mainOrderArea, BorderLayout.CENTER);

        // Left side: List of Active Orders
        JPanel activeOrdersPanel = new JPanel(new BorderLayout());
        JLabel activeOrdersLabel = new JLabel("Active Orders", SwingConstants.CENTER);
        activeOrdersPanel.add(activeOrdersLabel, BorderLayout.NORTH);

        DefaultListModel<Order> orderListModel = new DefaultListModel<>();
        JList<Order> activeOrderList = new JList<>(orderListModel);
        JScrollPane activeOrderScrollPane = new JScrollPane(activeOrderList);
        activeOrdersPanel.add(activeOrderScrollPane, BorderLayout.CENTER);

        // Right side: Order Details and Actions
        JPanel orderDetailsPanel = new JPanel(new BorderLayout());
        JLabel orderDetailsLabel = new JLabel("Order Details", SwingConstants.CENTER);
        orderDetailsPanel.add(orderDetailsLabel, BorderLayout.NORTH);

        JPanel orderDetailsContent = new JPanel(new BorderLayout()); // Use BorderLayout for better organization
        orderDetailsTextArea = new JTextArea(15, 30);
        orderDetailsTextArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(orderDetailsTextArea);
        orderDetailsContent.add(detailsScrollPane, BorderLayout.CENTER);

        JPanel orderActionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel for buttons
        JButton newOrderButton = new JButton("New Order");
        JButton addItemsButton = new JButton("Add Items");
        JButton editRequestsButton = new JButton("Edit Requests");
        JButton deleteOrderButton = new JButton("Delete Order"); // ADD THIS BUTTON
        orderActionsPanel.add(newOrderButton);
        orderActionsPanel.add(addItemsButton);
        orderActionsPanel.add(editRequestsButton);
        orderActionsPanel.add(deleteOrderButton);
        orderDetailsContent.add(orderActionsPanel, BorderLayout.SOUTH);

        orderDetailsPanel.add(orderDetailsContent, BorderLayout.CENTER);

        mainOrderArea.add(activeOrdersPanel);
        mainOrderArea.add(orderDetailsPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);
        backButton.addActionListener(e -> {
            refreshActiveOrders(orderListModel); // Refresh when leaving
            cardLayout.show(mainPanel, "mainApp");
        });
        orderPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Functionality:

        // Display details of the selected order
        activeOrderList.addListSelectionListener(e -> {
            Order selectedOrder = activeOrderList.getSelectedValue();
            if (selectedOrder != null) {
                orderDetailsTextArea.setText(selectedOrder.toString());
            }
        });

        // Create a new order (remains the same)
        newOrderButton.addActionListener(e -> {
            JPanel selectTablePanel = new JPanel(new FlowLayout());
            JComboBox<com.restaurant.table.Table> tableComboBox = new JComboBox<>();
            for (com.restaurant.table.Table table : tableManagement.getAllTables()) {
                if (table.getStatus().equalsIgnoreCase("Available")) { // Only available tables for new orders
                    tableComboBox.addItem(table);
                }
            }
            selectTablePanel.add(new JLabel("Select Table:"));
            selectTablePanel.add(tableComboBox);

            int result = JOptionPane.showConfirmDialog(RestaurantGUI.this, selectTablePanel,
                    "New Order - Select Table", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                com.restaurant.table.Table selectedTable = (com.restaurant.table.Table) tableComboBox.getSelectedItem();
                if (selectedTable != null) {
                    Order newOrder = orderManagement.createNewOrder(selectedTable.getTableNumber());
                    tableManagement.occupyTable(selectedTable.getTableNumber()); // Mark table as occupied
                    refreshActiveOrders(orderListModel);
                    orderDetailsTextArea.setText(newOrder.toString() + "\n\n--- Use 'Add Items' to add food ---");
                    activeOrderList.setSelectedValue(newOrder, true); // Select the new order
                } else {
                    JOptionPane.showMessageDialog(RestaurantGUI.this, "No table selected.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }

            refreshTablePanel();
        });

        // Button to navigate to the Menu panel to add items to the selected order
        addItemsButton.addActionListener(e -> {
            Order selectedOrder = activeOrderList.getSelectedValue();
            if (selectedOrder != null) {
                cardLayout.show(mainPanel, "menu");
                // Optionally, you could pass the selected order information to the menu panel
                // if you want to filter or highlight items for that order.
            } else {
                JOptionPane.showMessageDialog(RestaurantGUI.this, "Please select an order to add items to.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Button to edit special requests for the selected order
        editRequestsButton.addActionListener(e -> {
            Order selectedOrder = activeOrderList.getSelectedValue();
            if (selectedOrder != null) {
                String currentRequests = selectedOrder.getSpecialRequests();
                String newRequests = JOptionPane.showInputDialog(RestaurantGUI.this,
                        "Enter special requests:", currentRequests);
                if (newRequests != null) {
                    selectedOrder.setSpecialRequests(newRequests);
                    orderDetailsTextArea.setText(selectedOrder.toString()); // Update display
                }
            } else {
                JOptionPane.showMessageDialog(RestaurantGUI.this, "Please select an order to edit requests for.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Button to delete the selected order
        deleteOrderButton.addActionListener(e -> {
            Order selectedOrder = activeOrderList.getSelectedValue();
            if (selectedOrder != null) {
                int confirmResult = JOptionPane.showConfirmDialog(RestaurantGUI.this,
                        "Are you sure you want to delete Order ID: " + selectedOrder.getOrderId() + "?\nThis will also free the associated table.",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                    orderManagement.completeOrder(selectedOrder);
                    tableManagement.markTableAvailable(selectedOrder.getTableNumber()); // Free the table
                    refreshActiveOrders(orderListModel);
                    orderDetailsTextArea.setText(""); // Clear details
                }
            } else {
                JOptionPane.showMessageDialog(RestaurantGUI.this, "Please select an order to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            }

            refreshTablePanel();
        });

        // Initial load of active orders
        refreshActiveOrders(orderListModel);
    }

    private void refreshActiveOrders(DefaultListModel<Order> model) {
        model.removeAllElements();
        for (Order order : orderManagement.getAllActiveOrders()) {
            model.addElement(order);
        }
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
