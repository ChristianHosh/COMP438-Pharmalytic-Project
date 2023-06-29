package com.example.finalproject;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.globals.DatabaseController;
import com.example.finalproject.models.adapters.CategoryAdapter;
import com.example.finalproject.models.mockup.MockupData;


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
    }

    private void setUpCategoriesAdapter() {
        DatabaseController.initProducts(recyclerView_categories, this);
    }

    private void getViews() {
        recyclerView_categories = findViewById(R.id.dash_recyclerview_categories);
        button_search = findViewById(R.id.dash_btn_search);
        button_menu = findViewById(R.id.dash_btn_menu);
        editText_search = findViewById(R.id.dash_in_search_bar);
    }
}
