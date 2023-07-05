package com.example.finalproject.models;

public class CartItem {

    private Item item;
    private int quantity;
    private float price;

    public CartItem(Item item, int quantity, float price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.price = this.quantity * this.item.getPrice();
    }

    public float getPrice() {
        return price;
    }
}
