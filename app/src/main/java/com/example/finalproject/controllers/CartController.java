package com.example.finalproject.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.models.Item;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CartController {

    private static final String TAG = "CartController";

    // USER CART COLLECTION
    public static final String USER_COLLECTION_CART = "cart";
    public static final String USER_COLLECTION_CART_FIELD_ITEM_ID = "item_id";
    private static final String USER_COLLECTION_CART_FIELD_QUANTITY = "quantity";

    public static void addItemToCart(SessionController instance, Item item, Context context) {
        addItemToCart(instance, item, 1, context);
    }

    public static void addItemToCart(SessionController userInstance, Item item, int quantity, Context context) {
        String userUID = userInstance.getId();
        String itemUID = item.getId();

        Map<String, Object> cartItem = new HashMap<>();

        cartItem.put(USER_COLLECTION_CART_FIELD_ITEM_ID, itemUID);
        cartItem.put(USER_COLLECTION_CART_FIELD_QUANTITY, quantity);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();


        firestore
                .collection(ProductController.USER_COLLECTION)
                .document(userUID)
                .collection(USER_COLLECTION_CART)
                .document()
                .set(cartItem)
                .addOnCompleteListener(innerTask -> {
                    if (innerTask.isSuccessful()) {
                        Toast.makeText(context, "Item Added to cart", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "Error Adding Item to Cart => ", innerTask.getException());
                    }
                });

    }


}
