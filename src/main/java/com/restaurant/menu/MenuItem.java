package com.restaurant.menu;

public class MenuItem {
    private String name;
    private String description;
    private double price;
    private String category; // e.g., "Appetizer", "Main Course", "Dessert", "Beverage"

    public MenuItem(String name, String description, double price, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ") - " + description + " [" + category + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MenuItem menuItem = (MenuItem) obj;
        return Double.compare(menuItem.price, price) == 0 && name.equals(menuItem.name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != +0.0d ? Double.hashCode(price) : 0);
        return result;
    }
}
