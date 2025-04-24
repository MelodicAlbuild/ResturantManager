package com.restaurant.order;

import com.restaurant.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class OrderManagement {
    private List<Order> activeOrders;

    public OrderManagement() {
        this.activeOrders = new ArrayList<>();
    }

    public Order createNewOrder(int tableNumber) {
        Order newOrder = new Order(tableNumber);
        activeOrders.add(newOrder);
        System.out.println("New order created for table " + tableNumber + " with ID: " + newOrder.getOrderId());
        return newOrder;
    }

    // TODO: Implement method to retrieve an order by its ID
    public Order getOrderById(int orderId) {
        return null;
    }

    public List<Order> getOrdersByTable(int tableNumber) {
        List<Order> tableOrders = new ArrayList<>();
        for (Order order : activeOrders) {
            if (order.getTableNumber() == tableNumber) {
                tableOrders.add(order);
            }
        }
        return tableOrders;
    }

    public void addItemToOrder(Order order, MenuItem menuItem, int quantity) {
        if (order != null) {
            order.addItem(menuItem, quantity);
            System.out.println(quantity + " x " + menuItem.getName() + " added to order " + order.getOrderId());
        }
    }

    public void removeItemFromOrder(Order order, MenuItem menuItem, int quantity) {
        if (order != null) {
            order.removeItem(menuItem, quantity);
            System.out.println(quantity + " x " + menuItem.getName() + " removed from order " + order.getOrderId());
        }
    }

    public void updateOrderStatus(Order order, String newStatus) {
        if (order != null) {
            order.setOrderStatus(newStatus);
            System.out.println("Order " + order.getOrderId() + " status updated to: " + newStatus);
        }
    }

    public void completeOrder(Order order) {
        if (order != null) {
            order.setOrderStatus("Completed");
            System.out.println("Order " + order.getOrderId() + " completed.");
        }
    }

    public List<Order> getAllActiveOrders() {
        return new ArrayList<>(activeOrders); // Return a copy to prevent external modification
    }
}
