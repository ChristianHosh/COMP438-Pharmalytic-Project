package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.finalproject.controllers.ProductController;
import com.example.finalproject.models.Product;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
    private static final String TAG = "ADMIN";

    private Spinner spinner_category;

    private EditText editText_name;
    private EditText editText_desc;
    private EditText editText_xlDesc;
    private EditText editText_price;
    private EditText editText_quantity;
    private EditText editText_imagePath;

    private AppCompatButton button_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findViews();

        ArrayList<Product> products = new ArrayList<>(ProductController.products.values());

        ArrayAdapter<Product> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, products);
        spinner_category.setAdapter(arrayAdapter);

        button_add.setOnClickListener(e -> {
            Product category = (Product) spinner_category.getSelectedItem();
            String name = editText_name.getText().toString();
            String description = editText_desc.getText().toString();
            String xlDescription = editText_xlDesc.getText().toString();
            double price;
            int quantity;
            try {
                price = Double.parseDouble(editText_price.getText().toString());
                quantity = Integer.parseInt(editText_quantity.getText().toString());
            } catch (NumberFormatException exception) {
                Toast.makeText(this, "Wrong Number Format", Toast.LENGTH_SHORT).show();
                return;
            }

            String image_path = editText_imagePath.getText().toString();

            Map<String, Object> newItemDocument = new HashMap<>();
            newItemDocument.put(ProductController.ITEM_FIELD_NAME, name);
            newItemDocument.put(ProductController.ITEM_FIELD_DESC, description);
            newItemDocument.put(ProductController.ITEM_FIELD_XL_DESC, xlDescription);
            newItemDocument.put(ProductController.ITEM_FIELD_PRICE, price);
            newItemDocument.put(ProductController.ITEM_FIELD_QUANTITY, quantity);
            newItemDocument.put(ProductController.ITEM_FIELD_IMAGE_PATH, image_path);

            String product_id = category.getId();

            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            firestore
                    .collection(ProductController.PROD_COLLECTION)
                    .document(product_id)
                    .collection(ProductController.PROD_COLLECTION_ITEMS)
                    .document()
                    .set(newItemDocument)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Added New Item", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "Error getting product document: ", task.getException());
                        }
                    });
        });
    }

    private void findViews() {
        spinner_category = findViewById(R.id.admin_spinner);

        editText_name = findViewById(R.id.admin_edit_name);
        editText_desc = findViewById(R.id.admin_edit_desc);
        editText_xlDesc = findViewById(R.id.admin_edit_xl_desc);
        editText_price = findViewById(R.id.admin_edit_price);
        editText_quantity = findViewById(R.id.admin_edit_quantity);
        editText_imagePath = findViewById(R.id.admin_edit_image);

        button_add = findViewById(R.id.admin_btn_add);
    }
}