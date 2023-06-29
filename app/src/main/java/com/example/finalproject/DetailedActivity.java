package com.example.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.finalproject.models.Item;
import com.example.finalproject.models.adapters.ProductNestedAdapter;
import com.google.gson.Gson;

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

        getViews();
        Bundle bundle = getIntent().getExtras();
        String itemJson = bundle.getString(ProductNestedAdapter.ITEM_KEY, null);
        if (itemJson == null){
            finish();
        }

        item = new Gson().fromJson(itemJson, Item.class);

        setQuantity();

        textView_itemTitle.setText(item.getName());
        textView_itemInfo.setText(item.getXl_description());

        String productPrice = item.getPrice() + "$";
        textView_itemPrice.setText(productPrice);

        imageView_itemImage.setImageResource(R.drawable.unavailable_placeholder_image);
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
        numberPicker_quantity.setMaxValue(50);
        numberPicker_quantity.setValue(1);
        numberPicker_quantity.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int selectedQuantity = newVal - 1;
            if (newVal > 0) {
                button_addToCart.setClickable(true);
                button_addToCart.setOnClickListener(v -> addToCart(selectedQuantity));
            } else {
                button_addToCart.setClickable(false);
            }
        });

    }

    ///////////////////////////////////////////////////////////////////////////////////
    private void addToCart(int selectedQuantity) {
        if (selectedQuantity == 1) {
            Toast.makeText(getApplicationContext(), "Added to cart " + selectedQuantity + " item", Toast.LENGTH_SHORT).show();
        } else if (selectedQuantity > 1) {
            Toast.makeText(getApplicationContext(), "Added to cart " + selectedQuantity + " items", Toast.LENGTH_SHORT).show();
        }
    }
}
