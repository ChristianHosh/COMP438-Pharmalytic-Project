package com.example.finalproject.globals;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.finalproject.R;
import com.example.finalproject.models.Item;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CommonGlobal {

    public static class UI {
        public static final int BADGE_SUCCESS = R.drawable.badge_success;
        public static final int BADGE_DANGER = R.drawable.badge_danger;
        public static final int DROP_DOWN_ARROW = R.drawable.baseline_arrow_drop_down_24;
        public static final int DROP_UP_ARROW = R.drawable.baseline_arrow_drop_up_24;
    }

    public static class STRING {
        public static final String PASSWORD_SHORT = "Password should be at least 8 characters long!";
        public static final String REQUIRED_FIELD_STRING = "This is a required field!";
        public static final String PASSWORDS_DONT_MATCH_STRING = "Passwords should match!";
        public static final String EMAIL_NOT_VALID_STRING = "Email is not valid!";
        public static final String EMAIL_IN_USE = "Email is already in use!";
        public static final String INVALID_LOGIN = "Email or password is incorrect!";
    }

    public static class RECENT {
        public static final String PREF_KEY = "RECENT_ITEMS";
        public static final String ITEMS_KEY = "ITEMS";
        private static final ArrayList<Item> RECENT_ITEMS = new ArrayList<>();

        public static void setRecentItems(ArrayList<Item> recentItems, Activity activity){
            RECENT_ITEMS.clear();
            RECENT_ITEMS.addAll(recentItems);



            SharedPreferences preferences = activity.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            Gson gson = new Gson();

            String workoutsToJSON = gson.toJson(RECENT_ITEMS);

            editor.putString(ITEMS_KEY, workoutsToJSON);
            editor.apply();
        }

        public static ArrayList<Item> getRecentItems(){
            return RECENT_ITEMS;
        }
    }


}
