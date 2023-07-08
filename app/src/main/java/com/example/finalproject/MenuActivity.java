package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;

public class MenuActivity extends AppCompatActivity {
    private AppCompatButton button_user;
    private AppCompatButton button_home;
    private AppCompatButton button_cart;
    private AppCompatButton button_recent;
    private AppCompatButton button_settings;
    private AppCompatButton button_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }


}