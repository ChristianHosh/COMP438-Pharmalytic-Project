package com.example.finalproject.models.mockup;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MockCategory {

    private final int id;
    private final String name;

    private final ArrayList<MockItem> mockItems;

    public boolean isExpanded;

    public MockCategory(int id, String name) {
        this.id = id;
        this.name = name;

        mockItems = MockupData.getItemArrayList();
        isExpanded = false;
    }

    public MockCategory(int id, String name, List<MockItem> mockItems) {
        this.id = id;
        this.name = name;

        this.mockItems = new ArrayList<>(mockItems);

        isExpanded = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<MockItem> getProducts() {
        return mockItems;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
