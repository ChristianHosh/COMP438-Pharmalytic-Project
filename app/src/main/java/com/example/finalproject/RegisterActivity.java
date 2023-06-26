package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.finalproject.globals.CommonGlobal;
import com.example.finalproject.globals.DbKeys;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        setContentView(R.layout.activity_register);

        getViews();
        setMethods();
    }

    @Override
    public void onBackPressed() {
        showPopup();

    }

    private void setMethods() {
        buttonSignUp.setOnClickListener(e -> validateSignUpFields());
        textViewLoginRedirect.setOnClickListener(e -> redirectToLogin());
    }

    private void validateSignUpFields() {
        boolean canSignUp = true;
        String input_name = editTextNameInput.getText().toString().trim();
        String input_email = editTextEmailInput.getText().toString().trim();
        String input_password = editTextPassInput.getText().toString().trim();
        String input_conf_password = editTextConfPassInput.getText().toString().trim();

        // PUT SUCCESS BADGE FOR ALL EDITTEXT
        for (EditText editText : Arrays.asList(editTextPassInput, editTextConfPassInput, editTextNameInput, editTextEmailInput)) {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_SUCCESS, 0);
        }

        // REMOVE ERROR MESSAGES
        for (TextView textView : Arrays.asList(textViewConfPassError, textViewEmailError, textViewNameError, textViewPassError)) {
            textView.setText("");
        }

        // CHECK FIELDS FOR CORRECT INPUTS
        if (!input_password.equals(input_conf_password)) {
            editTextConfPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            editTextPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            // SHOW USER PASSWORDS DON'T MATCH
            textViewPassError.setText(CommonGlobal.STRING.PASSWORDS_DONT_MATCH_STRING);
            textViewConfPassError.setText(CommonGlobal.STRING.PASSWORDS_DONT_MATCH_STRING);
            canSignUp = false;
        }
        if (input_password.length() < 8) {
            editTextConfPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            editTextPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            //SHOW USER PASSWORD IS TOO SHORT
            textViewPassError.setText(CommonGlobal.STRING.PASSWORD_SHORT);
            textViewConfPassError.setText(CommonGlobal.STRING.PASSWORD_SHORT);

        }
        if (!input_email.contains("@") && input_email.length() < 6) {
            editTextEmailInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            // SHOW USER THAT EMAIL SHOULD CONTAIN '@'
            textViewEmailError.setText(CommonGlobal.STRING.EMAIL_NOT_VALID_STRING);
            canSignUp = false;
        }

        // CHECK FOR EACH FIELD IF IT'S EMPTY THEN PUT A DANGER BADGE AT THE RIGHT OF THE EDITTEXT
        if (input_name.equals("")) {
            editTextNameInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            // SHOW USER REQUIRED FIELD
            textViewNameError.setText(CommonGlobal.STRING.REQUIRED_FIELD_STRING);
            canSignUp = false;
        }
        if (input_email.equals("")) {
            editTextEmailInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            // SHOW USER REQUIRED FIELD
            textViewEmailError.setText(CommonGlobal.STRING.REQUIRED_FIELD_STRING);
        }
        if (input_password.equals("")) {
            editTextPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            // SHOW USER REQUIRED FIELD
            textViewPassError.setText(CommonGlobal.STRING.REQUIRED_FIELD_STRING);
            canSignUp = false;
        }
        if (input_conf_password.equals("")) {
            editTextConfPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
            // SHOW USER REQUIRED FIELD
            textViewConfPassError.setText(CommonGlobal.STRING.REQUIRED_FIELD_STRING);
        }

        // REGISTER USER TO DATABASE!!!
        if (!canSignUp) {
            return;
        }

        signupUser(input_name, input_email, input_password);
    }

    private void signupUser(String input_name, String input_email, String input_password) {
        FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();

        user.put(DbKeys.USER_FIELD_NAME, input_name);
        user.put(DbKeys.USER_FIELD_PASSWORD, input_password);
        user.put(DbKeys.USER_FIELD_EMAIL, input_email);
        user.put(DbKeys.USER_FIELD_REG_DATE, new Timestamp(new Date()));

        // QUERIES TO FIND DOCUMENTS IN THE USERS COLLECTION WHERE THEY ALREADY HAVE THE SAME EMAIL
        Query query = firestoreDB.collection(DbKeys.COL_USERS).whereEqualTo(DbKeys.USER_FIELD_EMAIL, input_email);

        AggregateQuery aggregateQuery = query.count();
        aggregateQuery.get(AggregateSource.SERVER)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // GETS THE QUERY RESULT
                        AggregateQuerySnapshot snapshot = task.getResult();

                        // IF THERE IS MORE THAN ZERO DOCUMENTS WITH THE SAME EMAIL
                        // THE USER WILL NOT BE ABLE TO REGISTER
                        // EMAIL IS ALREADY IN USE
                        long count = snapshot.getCount();

                        if (count > 0) {
                            // SHOW USER THAT EMAIL IS ALREADY IN USE
                            editTextEmailInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, CommonGlobal.UI.BADGE_DANGER, 0);
                            textViewEmailError.setText(CommonGlobal.STRING.EMAIL_IN_USE);

                            // DOES NOT CONTINUE TO FULLY REGISTER
                            return;
                        }

                        // IN THE OTHER CASE WHERE THE EMAIL IS NOT IN USE THEN THE USER REGISTERS
                        DocumentReference documentReference = firestoreDB.collection(DbKeys.COL_USERS).document();
                        documentReference.set(user)
                                .addOnSuccessListener(success -> {

                                    Log.d("REG SUCCESS", "DocumentSnapshot successfully written!");
                                    Toast.makeText(this
                                            , "Successful Registration!"
                                            , Toast.LENGTH_SHORT).show();

                                    redirectToLogin();

                                }).addOnFailureListener(exception -> {

                                    Log.d("REG ERROR", "DocumentSnapshot Failure: " + exception);
                                    Toast.makeText(this
                                            , "There was an error! Try again later"
                                            , Toast.LENGTH_SHORT).show();

                                });

                    } else {
                        Log.d("ERROR", "Accessing DB: ", task.getException());
                    }
                });
    }

    private void redirectToLogin() {
        // REDIRECTS TO LOGIN PAGE!!!
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }

    private void getViews() {
        buttonSignUp = findViewById(R.id.reg_btn_signup);
        editTextNameInput = findViewById(R.id.reg_in_name);
        editTextEmailInput = findViewById(R.id.reg_in_login_email);
        editTextPassInput = findViewById(R.id.reg_in_password);
        editTextConfPassInput = findViewById(R.id.reg_in_confirm_password);
        textViewLoginRedirect = findViewById(R.id.reg_text_login_redirect);

        textViewNameError = findViewById(R.id.reg_error_msg_name);
        textViewEmailError = findViewById(R.id.reg_error_msg_email);
        textViewPassError = findViewById(R.id.reg_error_msg_password);
        textViewConfPassError = findViewById(R.id.reg_error_msg_confirm_password);
    }

    private void showPopup() {
        // Inflate the layout for the custom popup

    }
}