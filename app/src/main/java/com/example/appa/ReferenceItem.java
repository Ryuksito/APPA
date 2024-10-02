package com.example.appa;

import java.io.Serializable;

public class ReferenceItem implements Serializable {
    private int id;
    private String tableName;

    // Constructor
    public ReferenceItem(int id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTableName() {
        return tableName;
    }
}
