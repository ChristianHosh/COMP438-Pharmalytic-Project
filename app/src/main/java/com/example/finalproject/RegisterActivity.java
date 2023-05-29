package com.example.finalproject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {
    private AppCompatButton buttonSignUp;
    private EditText editTextNameInput;
    private EditText editTextEmailInput;
    private EditText editTextPassInput;
    private EditText editTextConfPassInput;
    private TextView textViewLoginRedirect;
    private TextView textViewNameError;
    private TextView textViewEmailError;
    private TextView textViewPassError;
    private TextView textViewConfPassError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);

        getViews();
        setMethods();
    }

    private void setMethods() {
        buttonSignUp.setOnClickListener(e -> signUp());
        textViewLoginRedirect.setOnClickListener(e -> redirectToLogin());
    }

    private void signUp(){
        String input_name = editTextNameInput.getText().toString();
        String input_email = editTextEmailInput.getText().toString();
        String input_password = editTextPassInput.getText().toString();
        String input_conf_password = editTextConfPassInput.getText().toString();

        // PUT SUCCESS BADGE FOR ALL EDITTEXT
        for (EditText editText : Arrays.asList(editTextPassInput, editTextConfPassInput, editTextNameInput, editTextEmailInput)) {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, Global.BADGE_SUCCESS, 0);
        }

        // REMOVE ERROR MESSAGES
        for (TextView textView: Arrays.asList(textViewConfPassError,textViewEmailError,textViewNameError,textViewPassError)){
            textView.setText("");
        }

        // CHECK FOR EACH FIELD IF IT'S EMPTY THEN PUT A DANGER BADGE AT THE RIGHT OF THE EDITTEXT
        if (input_name.equals("")) {
            editTextNameInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, Global.BADGE_DANGER, 0);
            // SHOW USER REQUIRED FIELD
            textViewNameError.setText(Global.REQUIRED_FIELD_STRING);

        }
        if (input_email.equals("")) {
            editTextEmailInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, Global.BADGE_DANGER, 0);
            // SHOW USER REQUIRED FIELD
            textViewEmailError.setText(Global.REQUIRED_FIELD_STRING);
        }
        if (input_password.equals("")) {
            editTextPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, Global.BADGE_DANGER, 0);
            // SHOW USER REQUIRED FIELD
            textViewPassError.setText(Global.REQUIRED_FIELD_STRING);

        }
        if (input_conf_password.equals("")) {
            editTextConfPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, Global.BADGE_DANGER, 0);
            // SHOW USER REQUIRED FIELD
            textViewConfPassError.setText(Global.REQUIRED_FIELD_STRING);

        }


        // CHECK FIELDS FOR CORRECT INPUTS
        if (!input_password.equals(input_conf_password)) {
            editTextConfPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, Global.BADGE_DANGER, 0);
            editTextPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, Global.BADGE_DANGER, 0);
            // SHOW USER PASSWORDS DON'T MATCH
            textViewPassError.setText(Global.PASSWORDS_DONT_MATCH_STRING);
            textViewConfPassError.setText(Global.PASSWORDS_DONT_MATCH_STRING);

        }
        if (!input_email.contains("@")) {
            editTextEmailInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, Global.BADGE_DANGER, 0);
            // SHOW USER THAT EMAIL SHOULD CONTAIN '@'
            textViewEmailError.setText(Global.EMAIL_NOT_VALID_STRING);
        }


        // REGISTER USER TO DATABASE!!!
    }

    private void redirectToLogin(){
        Toast.makeText(this, "This Works!", Toast.LENGTH_SHORT).show();

        // REDIRECTS TO LOGIN PAGE!!!

    }
    private void getViews() {
        buttonSignUp = findViewById(R.id.btn_signup);
        editTextNameInput = findViewById(R.id.in_name);
        editTextEmailInput = findViewById(R.id.in_email);
        editTextPassInput = findViewById(R.id.in_password);
        editTextConfPassInput = findViewById(R.id.in_confirm_password);
        textViewLoginRedirect = findViewById(R.id.text_login_redirect);

        textViewNameError = findViewById(R.id.error_msg_name);
        textViewEmailError = findViewById(R.id.error_msg_email);
        textViewPassError = findViewById(R.id.error_msg_password);
        textViewConfPassError = findViewById(R.id.error_msg_confirm_password);
    }
}