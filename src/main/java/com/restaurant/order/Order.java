package com.restaurant.order;

import com.restaurant.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private List<OrderItem> items;
    private String specialRequests;
    private double totalAmount;
    private String orderStatus; // e.g., "Pending", "Preparing", "Served", "Paid"
    private int tableNumber; // Optional: If orders are associated with tables

    private static int nextOrderId = 1;

    public Order(int tableNumber) {
        this.orderId = nextOrderId++;
        this.items = new ArrayList<>();
        this.specialRequests = "";
        this.totalAmount = 0.0;
        this.orderStatus = "Pending";
        this.tableNumber = tableNumber;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void addItem(MenuItem menuItem, int quantity) {
        OrderItem orderItem = new OrderItem(menuItem, quantity);
        items.add(orderItem);
        this.totalAmount += menuItem.getPrice() * quantity;
    }

    public void removeItem(MenuItem menuItem, int quantityToRemove) {
        items.removeIf(item -> {
            if (item.getMenuItem().equals(menuItem)) {
                int currentQuantity = item.getQuantity();
                if (quantityToRemove >= currentQuantity) {
                    this.totalAmount -= menuItem.getPrice() * currentQuantity;
                    return true; // Remove the entire item
                } else {
                    item.setQuantity(currentQuantity - quantityToRemove);
                    this.totalAmount -= menuItem.getPrice() * quantityToRemove;
                    return false; // Keep the item with reduced quantity
                }
            }
            return false;
        });
    }

    // Helper class to represent an item in the order with its quantity
    public static class OrderItem {
        private MenuItem menuItem;
        private int quantity;

        public OrderItem(MenuItem menuItem, int quantity) {
            this.menuItem = menuItem;
            this.quantity = quantity;
        }

        public MenuItem getMenuItem() {
            return menuItem;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return menuItem.getName() + " x " + quantity + " @ $" + String.format("%.2f", menuItem.getPrice());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order ID: ").append(orderId)
                .append(", Table: ").append(tableNumber)
                .append(", Status: ").append(orderStatus)
                .append("\nItems:\n");
        for (OrderItem item : items) {
            sb.append("- ").append(item).append("\n");
        }
        sb.append("Special Requests: ").append(specialRequests)
                .append("\nTotal Amount: $").append(String.format("%.2f", totalAmount));
        return sb.toString();
    }
}
