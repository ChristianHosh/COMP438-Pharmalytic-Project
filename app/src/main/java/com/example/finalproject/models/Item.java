package com.example.finalproject.models;

import java.net.URI;

public class Item {
    private final String id;
    private final String name;
    private final String description;
    private final String xl_description;

    private int quantity;
    private final float price;

    private final URI image_path;

    public Item(String id, String name, String description, String xl_description, float price, URI image_path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.xl_description = xl_description;
//        this.quantity = quantity;
        this.price = price;
        this.image_path = image_path;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getXl_description() {
        return xl_description;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public URI getImage_path() {
        return image_path;
    }
}
