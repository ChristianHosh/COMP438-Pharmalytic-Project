package com.example.finalproject.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private final int id;
    private final String name;

    private final ArrayList<Product> products;

    public boolean isExpanded;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;

        products = MockupData.getProductsList();
        isExpanded = false;
    }

    public Category(int id, String name, List<Product> products) {
        this.id = id;
        this.name = name;

        this.products = new ArrayList<>(products);

        isExpanded = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
