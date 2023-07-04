package com.example.finalproject.globals;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.widget.AppCompatButton;

import com.example.finalproject.R;

public class ActivityController {
    public static void showExitConfirmationPopup(Activity activity) {
        Dialog mDialog = new Dialog(activity);

        mDialog.setContentView(R.layout.popup_confirm_exit);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton button_noExit = mDialog.findViewById(R.id.button_no_exit);
        AppCompatButton button_exit = mDialog.findViewById(R.id.button_exit);

        button_exit.setOnClickListener(e -> activity.finishAffinity());
        button_noExit.setOnClickListener(e -> mDialog.cancel());

        mDialog.show();
    }
}
