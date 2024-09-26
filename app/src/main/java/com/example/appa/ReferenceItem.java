package com.example.appa;

public class ReferenceItem {
    private int id;
    private String tableName;

    public ReferenceItem(int id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    public int getId() {
        return id;
    }

    public String getTableName() {
        return tableName;
    }
}
