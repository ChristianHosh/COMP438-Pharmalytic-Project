package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.finalproject.globals.CommonGlobal;
import com.example.finalproject.models.Item;
import com.example.finalproject.models.adapters.ItemAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecentActivity extends AppCompatActivity {

    private static final String TAG = "RecentActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        RecyclerView recyclerView = findViewById(R.id.recent_recycler);

        ArrayList<Item> recentItems = CommonGlobal.RECENT.getRecentItems();

        if (recentItems.size() == 0) {
            Log.d(TAG, "FROM SHARED PREFERENCES");

            SharedPreferences preferences = getSharedPreferences(CommonGlobal.RECENT.PREF_KEY, Context.MODE_PRIVATE);

            String itemsJson = preferences.getString(CommonGlobal.RECENT.ITEMS_KEY, null);
            Log.d(TAG, "ITEMS JSON => " + itemsJson);

            if (itemsJson == null) return;

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Item>>() {
            }.getType();

            ArrayList<Item> items = gson.fromJson(itemsJson, listType);

            ItemAdapter itemAdapter = new ItemAdapter(items, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(itemAdapter);
        } else {
            Log.d(TAG, "FROM RECENT ITEMS");

            ItemAdapter itemAdapter = new ItemAdapter(CommonGlobal.RECENT.getRecentItems(), this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(itemAdapter);
        }


    }
}