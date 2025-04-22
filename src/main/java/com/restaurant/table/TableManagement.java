package com.restaurant.table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableManagement {
    private List<Table> tables;

    public TableManagement(int numberOfTables) {
        this.tables = new ArrayList<>();
        for (int i = 1; i <= numberOfTables; i++) {
            tables.add(new Table(i));
        }
        System.out.println("Initialized " + numberOfTables + " tables.");
    }

    public List<Table> getAllTables() {
        return new ArrayList<>(tables); // Return a copy
    }

    public Table getTableByNumber(int tableNumber) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableNumber) {
                return table;
            }
        }
        System.out.println("Table " + tableNumber + " not found.");
        return null;
    }

    public List<Table> getAvailableTables() {
        return tables.stream()
                .filter(table -> table.getStatus().equalsIgnoreCase("Available"))
                .collect(Collectors.toList());
    }

    public void reserveTable(int tableNumber) {
        Table table = getTableByNumber(tableNumber);
        if (table != null) {
            if (table.getStatus().equalsIgnoreCase("Available")) {
                table.setStatus("Reserved");
                System.out.println("Table " + tableNumber + " reserved.");
            } else {
                System.out.println("Table " + tableNumber + " is not available for reservation (status: " + table.getStatus() + ").");
            }
        }
    }

    public void occupyTable(int tableNumber) {
        Table table = getTableByNumber(tableNumber);
        if (table != null) {
            if (table.getStatus().equalsIgnoreCase("Available") || table.getStatus().equalsIgnoreCase("Reserved")) {
                table.setStatus("Occupied");
                System.out.println("Table " + tableNumber + " occupied.");
            } else {
                System.out.println("Table " + tableNumber + " cannot be occupied (status: " + table.getStatus() + ").");
            }
        }
    }

    public void markTableAvailable(int tableNumber) {
        Table table = getTableByNumber(tableNumber);
        if (table != null) {
            table.setStatus("Available");
            System.out.println("Table " + tableNumber + " marked as available.");
        }
    }

    // TODO:
    // - Handling table reservations with specific times
    // - Tracking which order is associated with an occupied table
}
