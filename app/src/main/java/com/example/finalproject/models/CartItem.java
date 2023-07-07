package com.example.finalproject.models;

public class CartItem {

    private Item item;
    private int quantity;
    private final String id;
    private float price;

    public CartItem(Item item, String id, int quantity, float price) {
        this.item = item;
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getId() {
        return this.id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity() {
        this.quantity++;
        this.price = this.quantity * this.item.getPrice();
    }

    public void removeQuantity() {
        if (this.quantity < 0) return;

        this.quantity--;
        this.price = this.quantity * this.item.getPrice();
    }

    public float getPrice() {
        return price;
    }

}
