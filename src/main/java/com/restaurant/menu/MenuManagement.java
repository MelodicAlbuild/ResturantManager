package com.restaurant.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuManagement {
    private List<MenuItem> menuItems;

    public MenuManagement() {
        this.menuItems = new ArrayList<>();

        // TODO: Remove and move to main class
        // Add some default menu items
        addMenuItem(new MenuItem("Pizza Margherita", "Classic pizza with tomato, mozzarella, and basil", 12.99, "Main Course"));
        addMenuItem(new MenuItem("Cheeseburger", "Juicy beef patty with cheese, lettuce, and tomato", 9.99, "Main Course"));
        addMenuItem(new MenuItem("Caesar Salad", "Fresh romaine lettuce with Caesar dressing and croutons", 7.99, "Appetizer"));
        addMenuItem(new MenuItem("Chocolate Cake", "Rich chocolate cake with frosting", 5.99, "Dessert"));
        addMenuItem(new MenuItem("Soda", "Coke, Pepsi, Sprite", 2.50, "Beverage"));
    }

    public void addMenuItem(MenuItem item) {
        this.menuItems.add(item);
        System.out.println("Added to menu: " + item.getName());
    }

    public void removeMenuItem(String name) {
        menuItems.removeIf(item -> item.getName().equalsIgnoreCase(name));
        System.out.println("Removed from menu (if found): " + name);
    }

    public MenuItem getMenuItemByName(String name) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        System.out.println("Menu item '" + name + "' not found.");
        return null;
    }

    public List<MenuItem> getAllMenuItems() {
        return new ArrayList<>(menuItems); // Return a copy
    }

    public List<MenuItem> getMenuItemsByCategory(String category) {
        return menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public void updateMenuItem(String name, String newDescription, double newPrice, String newCategory) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setDescription(newDescription);
                item.setPrice(newPrice);
                item.setCategory(newCategory);
                System.out.println("Updated menu item: " + item.getName());
                return;
            }
        }
        System.out.println("Menu item '" + name + "' not found for update.");
    }
}
