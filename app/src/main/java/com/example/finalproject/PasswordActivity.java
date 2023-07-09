package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.controllers.ProductController;
import com.example.finalproject.controllers.SessionController;
import com.example.finalproject.globals.CommonGlobal;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class PasswordActivity extends AppCompatActivity {
    private EditText editText_currentPassword;
    private EditText editText_newPassword;
    private EditText editText_confirmNewPassword;
    private TextView textView_currentError;
    private TextView textView_newError;
    private TextView textView_confError;
    private AppCompatButton button_submit;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        findViews();

        button_submit.setOnClickListener(e -> validateFields());
    }

    private void findViews() {
        editText_currentPassword = findViewById(R.id.pass_et_current);
        editText_confirmNewPassword = findViewById(R.id.pass_et_confNew);
        editText_newPassword = findViewById(R.id.pass_et_new);

        textView_currentError = findViewById(R.id.pass_error_curr);
        textView_newError = findViewById(R.id.pass_error_new);
        textView_confError = findViewById(R.id.pass_error_conf);

        button_submit = findViewById(R.id.pass_btn_submit);
    }

    private void validateFields() {
        boolean canChange = true;

        String in_currentPassword = editText_currentPassword.getText().toString().trim();
        String in_newPassword = editText_newPassword.getText().toString().trim();
        String in_confNewPassword = editText_confirmNewPassword.getText().toString().trim();

        // PUT SUCCESS BADGE FOR ALL EDITTEXT
        for (EditText editText : Arrays.asList(editText_currentPassword, editText_newPassword, editText_confirmNewPassword)) {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_SUCCESS, 0);
        }
        for (TextView textView : Arrays.asList(textView_currentError, textView_newError, textView_confError)) {
            textView.setText("");
        }

        if (!in_newPassword.equals(in_confNewPassword)) {
            editText_confirmNewPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            editText_newPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            // SHOW USER PASSWORDS DON'T MATCH
            textView_newError.setText(CommonGlobal.STRING.PASSWORDS_DONT_MATCH_STRING);
            textView_confError.setText(CommonGlobal.STRING.PASSWORDS_DONT_MATCH_STRING);
            canChange = false;
        }
        if (in_newPassword.length() < 8) {
            editText_confirmNewPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            editText_newPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            //SHOW USER PASSWORD IS TOO SHORT
            textView_newError.setText(CommonGlobal.STRING.PASSWORD_SHORT);
            textView_confError.setText(CommonGlobal.STRING.PASSWORD_SHORT);
            canChange = false;
        }
        if (in_currentPassword.equals("")) {
            editText_currentPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            // SHOW USER REQUIRED FIELD
            textView_currentError.setText(CommonGlobal.STRING.REQUIRED_FIELD_STRING);
            canChange = false;
        }
        if (in_newPassword.equals("")) {
            editText_newPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            // SHOW USER REQUIRED FIELD
            textView_newError.setText(CommonGlobal.STRING.REQUIRED_FIELD_STRING);
        }
        if (in_confNewPassword.equals("")) {
            editText_confirmNewPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            // SHOW USER REQUIRED FIELD
            textView_confError.setText(CommonGlobal.STRING.REQUIRED_FIELD_STRING);
        }


        if (canChange) {
            changePassword(in_currentPassword, in_confNewPassword);
        }

    }

    private void changePassword(String in_currentPassword, String in_confNewPassword) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore
                .collection(ProductController.USER_COLLECTION)
                .document(SessionController.getInstance().getId())
                .get()
                .addOnSuccessListener(task -> {
                    String currentSavedPassword = task.getString(ProductController.USER_FIELD_PASSWORD);
                    if (!in_currentPassword.equals(currentSavedPassword)) {
                        textView_currentError.setText(CommonGlobal.STRING.INVALID_PASSWORD);
                        return;
                    }

                    firestore
                            .collection(ProductController.USER_COLLECTION)
                            .document(SessionController.getInstance().getId())
                            .update(ProductController.USER_FIELD_PASSWORD, in_confNewPassword)
                            .addOnSuccessListener(updateTask -> {
                                Toast.makeText(this, "Password Changed!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(this, SettingActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(exception -> Log.d("PasswordActivity", "FAILED TO CHANGE PASSWORD", exception));
                })
                .addOnFailureListener(exception -> Log.d("PasswordActivity", "FAILED TO GET CURRENT PASSWORD", exception));
    }
}