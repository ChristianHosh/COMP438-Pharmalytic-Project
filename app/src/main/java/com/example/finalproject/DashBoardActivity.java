package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.controllers.ProductController;
import com.example.finalproject.globals.ActivityController;
import com.example.finalproject.models.Item;

import java.util.ArrayList;


public class DashBoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView_categories;
    private AppCompatButton button_search;
    private AppCompatButton button_menu;
    private EditText editText_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getViews();

        setUpCategoriesAdapter();

        button_menu.setOnClickListener(e -> RedirectToMenu());
        button_search.setOnClickListener(e -> openSearchPopup());


    }



    private void openSearchPopup() {
        String queryString = editText_search.getText().toString().toLowerCase().trim();

        ArrayList<Item> filteredList = ProductController.getFilteredList(queryString);

        if (filteredList.isEmpty()){
            Toast.makeText(this, "No Items Found", Toast.LENGTH_SHORT).show();
            return;
        }



        DialogSearchList listDialog = new DialogSearchList(this, filteredList);

        // Show the dialog
        listDialog.show();

    }

    @Override
    public void onBackPressed() {
        ActivityController.showExitConfirmationPopup(this);
    }

    private void setUpCategoriesAdapter() {
        ProductController.initializeAllProducts(recyclerView_categories, this);
    }

    private void getViews() {
        recyclerView_categories = findViewById(R.id.dash_recyclerview_categories);
        button_search = findViewById(R.id.dash_btn_search);
        button_menu = findViewById(R.id.dash_btn_menu);
        editText_search = findViewById(R.id.dash_in_search_bar);
    }
    private void RedirectToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);

        finish();
    }
}
