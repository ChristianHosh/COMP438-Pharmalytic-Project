package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.finalproject.controllers.ProductController;
import com.example.finalproject.controllers.SessionController;
import com.example.finalproject.globals.ActivityController;
import com.example.finalproject.globals.CommonGlobal;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private AppCompatButton buttonLogin;
    private EditText editTextEmailInput;
    private EditText editTextPassInput;
    private TextView textViewEmailError;
    private TextView textViewPassError;
    private TextView textViewSignUpRedirect;

    ///
    private CheckBox checkBox;

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "USER_CRED";
    public static final String EMAIL = "EMAIL";
    public static final String PASS = "PASS";
    public static final String FLAG = "FLAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getViews();
        setMethods();

        checkSavedUser();
    }

    private void checkSavedUser() {
        sharedPreferences = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(EMAIL, "");
        String password = sharedPreferences.getString(PASS, "");
        boolean savedFlag = sharedPreferences.getBoolean(FLAG, false);

        if (savedFlag) {
            editTextEmailInput.setText(email);
            editTextPassInput.setText(password);
        }

        checkBox.setChecked(savedFlag);
    }


    private void getViews() {
        editTextEmailInput = findViewById(R.id.log_in_login_email);
        editTextPassInput = findViewById(R.id.log_in_password);
        textViewEmailError = findViewById(R.id.log_error_msg_email);
        textViewPassError = findViewById(R.id.log_error_msg_password);
        textViewSignUpRedirect = findViewById(R.id.log_text_register_redirect);
        buttonLogin = findViewById(R.id.log_btn_login);
        checkBox = findViewById(R.id.checkBox);

    }

    @Override
    public void onBackPressed() {
        ActivityController.showExitConfirmationPopup(this);
    }


    private void setMethods() {
        buttonLogin.setOnClickListener(e -> validateLogInFields());
        textViewSignUpRedirect.setOnClickListener(e -> redirectToSignUp());
    }

    private void redirectToHome() {
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

        if (checkBox.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(EMAIL, input_email);
            editor.putString(PASS, input_password);
            editor.putBoolean(FLAG, true);
            editor.apply();

        }


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
        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

        firestoreDB
                .collection(ProductController.USER_COLLECTION)
                .whereEqualTo(ProductController.USER_FIELD_EMAIL, input_email)
                .whereEqualTo(ProductController.USER_FIELD_PASSWORD, input_password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        try {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);

                            SessionController.setInstance(document.getId());
                            redirectToHome();

                            // IF IT RETURNS AN EMPTY ARRAY THEN THERE WERE NO DOCUMENTS
                            // WHICH MEANS THERE IS NOT USER WITH THAT LOGIN INFO
                        } catch (IndexOutOfBoundsException e) {
                            // SHOW USER THAT EMAIL OR PASSWORD ARE INCORRECT
                            editTextEmailInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
                            textViewEmailError.setText(CommonGlobal.STRING.INVALID_LOGIN);

                            editTextPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
                            textViewPassError.setText(CommonGlobal.STRING.INVALID_LOGIN);
                        }
                    }

                });


    }
}
