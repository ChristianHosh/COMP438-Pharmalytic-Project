package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.finalproject.controllers.CartController;
import com.example.finalproject.controllers.SessionController;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView_cartItems;
    private TextView textView_totalPrice;
    private AppCompatButton button_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        findViews();

        CartController.initializeCartItemsAndPrice(SessionController.getInstance(),
                recyclerView_cartItems, textView_totalPrice, this);

        button_checkout.setOnClickListener(e -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);

        });
    }

    private void findViews() {
        recyclerView_cartItems = findViewById(R.id.cart_recycler);
        textView_totalPrice = findViewById(R.id.cart_textView_total);
        button_checkout = findViewById(R.id.cart_btn_checkout);

    }
}