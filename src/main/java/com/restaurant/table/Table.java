package com.restaurant.table;

public class Table {
    private int tableNumber;
    private String status; // "Available", "Reserved", "Occupied"

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.status = "Available";
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Table " + tableNumber + " (" + status + ")";
    }
}
