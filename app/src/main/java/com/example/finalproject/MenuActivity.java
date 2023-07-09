package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
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

        button_user = findViewById(R.id.menu_btn_user);
        button_home = findViewById(R.id.menu_btn_home);
        button_cart = findViewById(R.id.menu_btn_cart);
        button_recent = findViewById(R.id.menu_btn_recenets);
        button_logout = findViewById(R.id.menu_btn_logout);

        setOnClickListeners();
    }

    private void redirectToSettingActivity() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectToDash() {
        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectToAdmin(){
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectToRecent(){
        Intent intent = new Intent(this, Recent.class);
        startActivity(intent);
        finish();
    }


    private void setOnClickListeners() {
        button_user.setOnClickListener(view -> redirectToSettingActivity());
        button_logout.setOnClickListener(view -> redirectToLoginActivity());
        button_home.setOnClickListener(view -> redirectToDash());
        button_recent.setOnClickListener(view-> redirectToRecent());
    }
}