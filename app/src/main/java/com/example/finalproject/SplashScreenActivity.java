package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Animation text = AnimationUtils.loadAnimation(this, R.anim.text_animation);
        Animation logo = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        TextView txtTitle = findViewById(R.id.splash_title);
        ImageView imgLogo = findViewById(R.id.spash_logo);

        txtTitle.setAnimation(text);
        imgLogo.setAnimation(logo);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}