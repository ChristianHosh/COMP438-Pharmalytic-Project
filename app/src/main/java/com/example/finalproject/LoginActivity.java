package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private AppCompatButton buttonLogin;
    private EditText editTextEmailInput;
    private EditText editTextPassInput;
    private TextView textViewEmailError;
    private TextView textViewPassError;
    private TextView textViewSignUpRedirect;
    private TextView textViewForgetPasswordRedirect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViews();
        setMethods();
    }

    private void getViews() {
        editTextEmailInput = findViewById(R.id.log_in_login_email);
        editTextPassInput = findViewById(R.id.log_in_password);
        textViewEmailError = findViewById(R.id.log_error_msg_email);
        textViewPassError = findViewById(R.id.log_error_msg_password);

        textViewSignUpRedirect = findViewById(R.id.log_text_register_redirect);
        textViewForgetPasswordRedirect = findViewById(R.id.log_text_forget_pass_redirect);

        buttonLogin = findViewById(R.id.log_btn_login);
    }

    @Override
    public void onBackPressed() {
        showPopup();

    }

    private void showPopup() {

    }

    private void setMethods() {
        buttonLogin.setOnClickListener(e -> validateLogInFields());

        textViewSignUpRedirect.setOnClickListener(e -> redirectToSignUp());
        textViewForgetPasswordRedirect.setOnClickListener(e -> redirectToForgetPassword());
    }

    private void redirectToForgetPassword() {
        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);

        finish();
    }

    private void redirectToSignUp() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

        finish();
    }


    private void validateLogInFields() {
        boolean canLogin = true;
        String input_email = editTextEmailInput.getText().toString().trim();
        String input_password = editTextPassInput.getText().toString().trim();


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
            canLogin = false;
        }

        if (input_email.equals("")) {
            editTextEmailInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            textViewEmailError.setText(CommonGlobal.STRING.REQUIRED_FIELD_STRING);
        }
        if (input_password.equals("")) {
            editTextPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            textViewPassError.setText(CommonGlobal.STRING.REQUIRED_FIELD_STRING);
            canLogin = false;
        }

        if (!canLogin) {
            return;
        }

        loginUser(input_email, input_password);
    }

    private void loginUser(String input_email, String input_password) {

    }
}
