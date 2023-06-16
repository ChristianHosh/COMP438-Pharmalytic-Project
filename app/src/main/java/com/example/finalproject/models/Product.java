package com.example.finalproject.models;

public class Product {
    private final int id;
    private final String title;
    private final String description;

    private final double price;
    private final int quantity;

    private String imagePath;
    private int categoryId;

    public Product(int id, String title, String description, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
