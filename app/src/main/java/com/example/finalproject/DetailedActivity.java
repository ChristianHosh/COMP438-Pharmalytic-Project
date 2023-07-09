package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.finalproject.controllers.CartController;
import com.example.finalproject.controllers.SessionController;
import com.example.finalproject.models.Item;
import com.example.finalproject.models.adapters.ItemAdapter;
import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;

public class DetailedActivity extends Activity {

    private TextView textView_itemTitle;
    private TextView textView_itemInfo;
    private TextView textView_itemPrice;
    private AppCompatButton button_addToCart;
    private ImageView imageView_itemImage;
    private NumberPicker numberPicker_quantity;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        Slidr.attach(this, Color.TRANSPARENT, Color.TRANSPARENT);

        getViews();
        Bundle bundle = getIntent().getExtras();
        String itemJson = bundle.getString(ItemAdapter.ITEM_KEY, null);
        if (itemJson == null) {
            finish();
        }

        item = new Gson().fromJson(itemJson, Item.class);

        setQuantity();

        textView_itemTitle.setText(item.getName());
        textView_itemInfo.setText(item.getXl_description());

        String productPrice = item.getPrice() + "$";
        textView_itemPrice.setText(productPrice);


        Glide.with((Context) this)
                .load(item.getImage_path().toString())
                .placeholder(R.drawable.loading_spinner)
                .error(R.drawable.unavailable_placeholder_image)
                .into(imageView_itemImage);
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
        numberPicker_quantity.setMinValue(1);
        numberPicker_quantity.setMaxValue(25);
        numberPicker_quantity.setValue(1);
        numberPicker_quantity.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (newVal > 0) {
                button_addToCart.setClickable(true);
                button_addToCart.setOnClickListener(v -> addToCart(newVal));
            } else {
                button_addToCart.setClickable(false);
            }
        });

    }

    ///////////////////////////////////////////////////////////////////////////////////
    private void addToCart(int selectedQuantity) {
        if (selectedQuantity == 1) {
            CartController.addItemToCart(SessionController.getInstance(), item, this);

        } else if (selectedQuantity > 1) {
            CartController.addItemToCart(SessionController.getInstance(), item, selectedQuantity, this);

        }
    }
}
