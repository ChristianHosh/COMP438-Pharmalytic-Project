package com.example.finalproject.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Product {
    private final String id;
    private final String name;
    private final ArrayList<Item> items;

    public boolean isExpanded;
    public boolean isLoaded;

    public Product(String id, String name) {
        this.id = id;
        this.name = name;

        items = new ArrayList<>();
        this.isExpanded = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

}
