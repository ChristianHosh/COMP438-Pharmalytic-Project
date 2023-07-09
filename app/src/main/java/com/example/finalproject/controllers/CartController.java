package com.example.finalproject.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.CartItem;
import com.example.finalproject.models.Item;
import com.example.finalproject.models.Product;
import com.example.finalproject.models.adapters.CartItemAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartController {

    private static final String TAG = "CartController";

    // USER CART COLLECTION
    public static final String USER_COLLECTION_CART = "cart";
    public static final String USER_COLLECTION_CART_FIELD_ITEM_ID = "item_id";
    public static final String USER_COLLECTION_CART_FIELD_QUANTITY = "quantity";
    public static final String USER_COLLECTION_CART_FIELD_TOTAL_PRICE = "total";
    private static final ArrayList<CartItem> CART_ITEMS = new ArrayList<>();


    public static void addItemToCart(SessionController instance, Item item, Context context) {
        addItemToCart(instance, item, 1, context);
    }

    public static void addItemToCart(SessionController userInstance, Item item, int quantity, Context context) {
        String userUID = userInstance.getId();
        String itemUID = item.getId();

        double totalPrice = item.getPrice() * quantity;

        Map<String, Object> cartItem = new HashMap<>();

        cartItem.put(USER_COLLECTION_CART_FIELD_ITEM_ID, itemUID);
        cartItem.put(USER_COLLECTION_CART_FIELD_QUANTITY, quantity);
        cartItem.put(USER_COLLECTION_CART_FIELD_TOTAL_PRICE, totalPrice);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore
                .collection(ProductController.USER_COLLECTION)
                .document(userUID)
                .collection(USER_COLLECTION_CART)
                .document()
                .set(cartItem)
                .addOnCompleteListener(innerTask -> {
                    if (innerTask.isSuccessful()) {
                        if (quantity > 1)
                            Toast.makeText(context, "Added to cart " + quantity + " items", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Added to cart " + quantity + " item", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.d(TAG, "Error Adding Item to Cart => ", innerTask.getException());
                    }
                });

    }

    public static void initializeCartItemsAndPrice(SessionController userInstance, RecyclerView recyclerView_cartItems, TextView textView_price, Context context) {
        ArrayList<CartItem> cartItems = new ArrayList<>();

        String userUID = userInstance.getId();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore
                .collection(ProductController.USER_COLLECTION)
                .document(userUID)
                .collection(USER_COLLECTION_CART)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String id = document.getId();
                            String itemID = (String) document.getData().get(USER_COLLECTION_CART_FIELD_ITEM_ID);
                            Long itemQuantity = document.getLong(USER_COLLECTION_CART_FIELD_QUANTITY);
                            Double itemPrice = document.getDouble(USER_COLLECTION_CART_FIELD_TOTAL_PRICE);

                            for (Product product : ProductController.products.values()) {
                                for (Item item : product.getItems()) {
                                    if (!item.getId().equals(itemID))
                                        continue;

                                    assert itemQuantity != null;
                                    assert itemPrice != null;

                                    CartItem cartItem = new CartItem(item, id, itemQuantity.intValue(), itemPrice.floatValue());
                                    cartItems.add(cartItem);
                                    CART_ITEMS.add(cartItem);
                                }
                            }
                        }

                        CartItemAdapter adapter = new CartItemAdapter(cartItems, context, textView_price);

                        recyclerView_cartItems.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView_cartItems.setAdapter(adapter);

                        float total = 0;
                        for (CartItem cartItem : cartItems) {
                            total += cartItem.getPrice();
                        }
                        String priceString = total + "$";
                        textView_price.setText(priceString);
                    }
                });

    }

    public static void updateTotal(ArrayList<CartItem> cartItems, TextView textView_price) {
        float total = 0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getPrice();
        }
        String priceString = total + "$";
        textView_price.setText(priceString);
    }

    public static ArrayList<CartItem> getCartItems() {
        return CART_ITEMS;
    }

    public static void orderPlaced() {
        CART_ITEMS.clear();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore
                .collection(ProductController.USER_COLLECTION)
                .document(SessionController.getInstance().getId())
                .collection(USER_COLLECTION_CART)
                .get()
                .addOnSuccessListener(task -> {
                    for (QueryDocumentSnapshot document : task) {
                        firestore
                                .collection(ProductController.USER_COLLECTION)
                                .document(SessionController.getInstance().getId())
                                .collection(USER_COLLECTION_CART)
                                .document(document.getId())
                                .delete()
                                .addOnSuccessListener(unused -> {

                                });
                    }
                });
    }
}
