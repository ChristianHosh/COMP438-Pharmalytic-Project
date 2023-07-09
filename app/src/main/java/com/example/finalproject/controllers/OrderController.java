package com.example.finalproject.controllers;

import android.util.Log;

import com.example.finalproject.models.CartItem;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderController {

    private static final String ORDER_COLLECTION = "orders";
    private static final String ORDER_FIELD_USER = "user_id";
    private static final String ORDER_FIELD_STREET = "address_street";
    private static final String ORDER_FIELD_CITY = "address_city";
    private static final String ORDER_FIELD_STATE = "address_state";
    private static final String ORDER_FIELD_ZIPCODE = "address_zipcode";
    private static final String ORDER_FIELD_CARD_NUMBER = "card_number";
    private static final String ORDER_FIELD_CARD_DATE = "card_date";
    private static final String ORDER_FIELD_CARD_CODE = "card_code";
    private static final String ORDER_FIELD_ITEM_IDS = "item_id";

    public static void placeOrder(SessionController instance, String in_addressDelivery,
                                  String in_addressCity, String in_addressState,
                                  String in_addressZip, String in_cardNumber, String in_cardDate,
                                  String in_cardCode, ArrayList<CartItem> cartItems) {

        ArrayList<String> itemsIds = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            itemsIds.add(cartItem.getItem().getId());
        }

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put(ORDER_FIELD_USER, instance.getId());
        orderMap.put(ORDER_FIELD_STREET, in_addressDelivery);
        orderMap.put(ORDER_FIELD_CITY, in_addressCity);
        orderMap.put(ORDER_FIELD_STATE, in_addressState);
        orderMap.put(ORDER_FIELD_ZIPCODE, in_addressZip);
        orderMap.put(ORDER_FIELD_CARD_NUMBER, in_cardNumber);
        orderMap.put(ORDER_FIELD_CARD_DATE, in_cardDate);
        orderMap.put(ORDER_FIELD_CARD_CODE, in_cardCode);
        orderMap.put(ORDER_FIELD_ITEM_IDS, itemsIds);


        firestore
                .collection(ORDER_COLLECTION)
                .document()
                .set(orderMap)
                .addOnSuccessListener(result -> {
                    Log.w("OrderController", "PLACED ORDER");
                    CartController.orderPlaced();
                })
                .addOnFailureListener(exception -> Log.d("OrderController", "ERROR CREATING ORDER => ", exception));
    }
}
