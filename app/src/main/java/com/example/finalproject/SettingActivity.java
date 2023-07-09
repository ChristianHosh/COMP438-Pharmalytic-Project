package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    private AppCompatButton button_change;
    private AppCompatButton button_back2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        button_change = findViewById(R.id.user_btn_changePass);
        button_back2 = findViewById(R.id.user_btn_back);
        setOnClickListener();
    }

    private void redirectToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectToPasswordActivity() {
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
        finish();
    }

    private void setOnClickListener() {
        button_back2.setOnClickListener(view -> redirectToMenu());
        button_change.setOnClickListener(view -> redirectToPasswordActivity());
    }
}