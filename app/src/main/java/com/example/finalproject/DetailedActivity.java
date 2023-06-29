package com.example.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

public class DetailedActivity extends Activity {

    TextView textView_itemTitle;
    TextView textView_itemInfo;
    TextView textView_itemPrice;
    AppCompatButton button_addToCart;
    ImageView imageView_itemImage;

    NumberPicker numberPicker_quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        getViews();
        setQuantity();
    }

    private void getViews() {
        textView_itemTitle = findViewById(R.id.detailed_title);
        imageView_itemImage = findViewById(R.id.detailed_img);
        textView_itemInfo = findViewById(R.id.detailed_info);
        textView_itemPrice = findViewById(R.id.detailed_price);
        button_addToCart = findViewById(R.id.addToCart);
        numberPicker_quantity = findViewById(R.id.quantity);
    }

    private void setQuantity() {
        button_addToCart.setClickable(false);
        numberPicker_quantity.setMinValue(0);
        numberPicker_quantity.setMaxValue(50);
        numberPicker_quantity.setValue(0);
        numberPicker_quantity.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int selectedQuantity = newVal - 1;
            if (newVal > 0) {
                button_addToCart.setClickable(true);
                button_addToCart.setOnClickListener(v -> btnAddToCart(selectedQuantity));
            } else {
                button_addToCart.setClickable(false);
            }
        });

    }

    ///////////////////////////////////////////////////////////////////////////////////
    private void btnAddToCart(int selectedQuantity) {
        if (selectedQuantity == 1) {
            Toast.makeText(getApplicationContext(), "Added to cart " + selectedQuantity + " item", Toast.LENGTH_SHORT).show();
        } else if (selectedQuantity > 1) {
            Toast.makeText(getApplicationContext(), "Added to cart " + selectedQuantity + " items", Toast.LENGTH_SHORT).show();
        }
    }
}
