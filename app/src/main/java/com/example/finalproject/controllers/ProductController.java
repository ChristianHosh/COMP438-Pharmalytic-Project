package com.example.finalproject.controllers;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.models.Item;
import com.example.finalproject.models.Product;
import com.example.finalproject.models.adapters.CategoryAdapter;
import com.example.finalproject.models.adapters.ProductNestedAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductController {
    private static final String TAG = "DB_CONTROL";

    //    USER FIELD KEYS
    public static final String USER_COLLECTION = "users";

    public static final String USER_FIELD_EMAIL = "email";
    public static final String USER_FIELD_PASSWORD = "password";
    public static final String USER_FIELD_NAME = "name";
    public static final String USER_FIELD_REG_DATE = "reg_date";



    //    PRODUCT FIELD KEYS
    public static final String PROD_COLLECTION = "products";
    public static final String PROD_FIELD_NAME = "name";
    public static final String PROD_COLLECTION_ITEMS = "items";


    //    ITEM FIELD KEYS
    public static final String ITEM_FIELD_NAME = "name";
    public static final String ITEM_FIELD_DESC = "description";
    public static final String ITEM_FIELD_XL_DESC = "xl_description";
    public static final String ITEM_FIELD_PRICE = "price";
    public static final String ITEM_FIELD_QUANTITY = "quantity";
    public static final String ITEM_FIELD_IMAGE_PATH = "image_path";


    public static HashMap<String, Product> products = new HashMap<>();

    public static void initializeAllProducts(RecyclerView recyclerView, Context context) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        ArrayList<Product> productArrayList = new ArrayList<>();
        firestore.collection(PROD_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String name = (String) document.getData().get(PROD_FIELD_NAME);
                            Product newProduct = new Product(id, name);

                            productArrayList.add(newProduct);
                            products.put(id, newProduct);
                        }

                        CategoryAdapter categoryAdapter = new CategoryAdapter(productArrayList, context);

                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(categoryAdapter);

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public static void initializeAllItemsFromProduct(Product product, RecyclerView recyclerView, Context context, Context nestedContext) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        ArrayList<Item> itemArrayList = new ArrayList<>();

        if (product.isLoaded) {
            Log.d(TAG, "ITEMS ALREADY LOADED FOR " + product);
            itemArrayList = product.getItems();
            if (!itemArrayList.isEmpty()) {
                ProductNestedAdapter nestedAdapter = new ProductNestedAdapter(itemArrayList, context);
                recyclerView.setLayoutManager(new LinearLayoutManager(nestedContext));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(nestedAdapter);
            }
            return;
        }


        ArrayList<Item> finalItemArrayList = itemArrayList;
        firestore.collection(PROD_COLLECTION)
                .document(product.getId())
                .collection(PROD_COLLECTION_ITEMS)
                .whereGreaterThan(ITEM_FIELD_QUANTITY, 0)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "FETCHING COLLECTION FROM PRODUCT ID => " + product.getId());
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, "GETTING DOCUMENT: " + document.getId() + " => " + document.getData());
                            String id = document.getId();
                            String name = document.getString(ITEM_FIELD_NAME);
                            String description = document.getString(ITEM_FIELD_DESC);
                            String xlDescription = document.getString(ITEM_FIELD_XL_DESC);
                            Double price = document.getDouble(ITEM_FIELD_PRICE);
                            URI image_path = null;
                            try {
                                image_path = new URI(document.getString(ITEM_FIELD_IMAGE_PATH));
                            } catch (URISyntaxException e) {
                                Log.d(TAG, "URI EXCEPTION " + e);
                            }

                            assert price != null;
                            Item newItem = new Item(id, name, description, xlDescription, price.floatValue(), image_path);

                            finalItemArrayList.add(newItem);
                            product.getItems().clear();
                            product.getItems().addAll(finalItemArrayList);
                        }

                        ProductNestedAdapter nestedAdapter = new ProductNestedAdapter(finalItemArrayList, context);
                        recyclerView.setLayoutManager(new LinearLayoutManager(nestedContext));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(nestedAdapter);
                        product.isLoaded = true;
                    } else {
                        Log.d(TAG, "Error getting item documents: ", task.getException());
                    }
                });

    }



//    public static void


}
