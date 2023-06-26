package com.example.finalproject;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    private Button buttonHome;
    private Button buttonUser;
    private Button buttonMessages;
    private Button buttonLocation;
    private Button buttonSettings;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonHome = findViewById(R.id.button_Home);
        buttonUser = findViewById(R.id.button_user);
        buttonMessages = findViewById(R.id.button_messages);
        buttonLocation = findViewById(R.id.button_location);
        buttonSettings = findViewById(R.id.button_setting);
        buttonLogout = findViewById(R.id.button_Logout);

        // Set click listeners for the buttons
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Home button click
            }
        });

        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        buttonMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
