package com.example.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class DetailedActivity extends Activity {

    TextView detailed_title;
    ImageView detailed_img;
    TextView datailed_info;

    TextView detailed_price;
    Button addToCart;

    NumberPicker quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_activity);
        getViews();
        setQuantity();
    }
    private void getViews() {
        detailed_title = findViewById(R.id.detailed_title);
        detailed_img = findViewById(R.id.detailed_img);
        datailed_info = findViewById(R.id.datailed_info);
        detailed_price = findViewById(R.id.detailed_price);
        addToCart = findViewById(R.id.addToCart);
        quantity = findViewById(R.id.quantity);
    }

    private void setQuantity()
    {
        addToCart.setClickable(false);
        quantity.setMinValue(0);
        quantity.setMaxValue(60);
        quantity.setValue(0);
        quantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int selectedQuantity = newVal-1;
                if (newVal>0) {
                    addToCart.setClickable(true);
                    addToCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnAddToCart(selectedQuantity);
                        }
                    });
                }
            }
        });

    }
       ///////////////////////////////////////////////////////////////////////////////////
    private void btnAddToCart(int selectedQuantity) {
        if (selectedQuantity == 1) {
            Toast.makeText(getApplicationContext(), "Added to cart " + selectedQuantity + " item", Toast.LENGTH_SHORT).show();
        } else if (selectedQuantity>1) {
            Toast.makeText(getApplicationContext(), "Added to cart " + selectedQuantity + " items", Toast.LENGTH_SHORT).show();
        }
    }
}
