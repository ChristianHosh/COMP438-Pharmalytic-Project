package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private AppCompatButton buttonLogIn;
    private EditText editTextEmailInput;
    private EditText editTextPassInput;
    private TextView textViewSignUpRedirect;
    private TextView textViewEmailError;
    private TextView textViewPassError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViews();
        setMethods();
    }

    private void getViews() {
        editTextEmailInput = findViewById(R.id.in_login_email);
        editTextPassInput = findViewById(R.id.in_password);
        textViewEmailError = findViewById(R.id.error_msg_email);
        textViewPassError = findViewById(R.id.error_msg_password);
    }

    @Override
    public void onBackPressed() {
        showPopup();

    }

    private void showPopup() {
    }

    private void setMethods() {
        buttonLogIn.setOnClickListener(e -> validateLogInFields());
        textViewSignUpRedirect.setOnClickListener(e -> redirectToSignUp());
    }

    private void redirectToSignUp() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

        finish();
    }


    private void validateLogInFields() {
        boolean canLogIn = true;
        String input_email = editTextEmailInput.getText().toString();
        String input_password = editTextPassInput.getText().toString();


        for (EditText editText : Arrays.asList(editTextPassInput, editTextEmailInput)) {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_SUCCESS, 0);
        }

        // REMOVE ERROR MESSAGES
        for (TextView textView : Arrays.asList(textViewEmailError, textViewPassError)) {
            textView.setText("");
        }


        if (!input_email.contains("@") && input_email.length() < 6) {
            editTextEmailInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            // SHOW USER THAT EMAIL SHOULD CONTAIN '@'
            textViewEmailError.setText(CommonGlobal.STRING.EMAIL_NOT_VALID_STRING);
            canLogIn = false;
        }

        if (input_email.equals("")) {
            editTextEmailInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            textViewEmailError.setText(CommonGlobal.STRING.REQUIRED_FIELD_STRING);
        }
        if (input_password.equals("")) {
            editTextPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            textViewPassError.setText(CommonGlobal.STRING.REQUIRED_FIELD_STRING);
            canLogIn = false;
        }

        if (!canLogIn){
            return;
        }



    }
}
