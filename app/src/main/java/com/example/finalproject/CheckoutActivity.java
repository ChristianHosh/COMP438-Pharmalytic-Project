package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.controllers.CartController;
import com.example.finalproject.controllers.OrderController;
import com.example.finalproject.controllers.SessionController;

public class CheckoutActivity extends AppCompatActivity implements
        AddressFragment.AddressFragmentListener,
        PaymentFragment.PaymentFragmentListener,
        ReviewFragment.ReviewListener {

    private ImageView imageView_addressStep;
    private ImageView imageView_paymentStep;
    private ImageView imageView_reviewStep;
    private View view_addressLine;
    private View view_paymentLine;
    @SuppressWarnings("FieldCanBeLocal")
    private AddressFragment addressFragment;
    private PaymentFragment paymentFragment;
    private ReviewFragment reviewFragment;


    private String in_addressDelivery;
    private String in_addressCity;
    private String in_addressState;
    private String in_addressZip;
    private String in_cardNumber;
    private String in_cardDate;
    private String in_cardCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        findViews();

        addressFragment = new AddressFragment();
        paymentFragment = new PaymentFragment();
        reviewFragment = new ReviewFragment();

        imageView_addressStep.setBackgroundResource(R.drawable.baseline_radio_button_checked_24);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.checkout_fragmentFrame, addressFragment)
                .commit();


    }

    private void findViews() {
        imageView_addressStep = findViewById(R.id.checkout_iv_addressStep);
        imageView_paymentStep = findViewById(R.id.checkout_iv_paymentStep);
        imageView_reviewStep = findViewById(R.id.checkout_iv_reviewStep);
        view_addressLine = findViewById(R.id.checkout_v_addressStepLine);
        view_paymentLine = findViewById(R.id.checkout_v_paymentStepLine);

    }

    @Override
    public void onAddressSent(String delivery, String city, String state, String zip) {
        imageView_paymentStep.setBackgroundResource(R.drawable.baseline_radio_button_checked_24);
        view_addressLine.setBackgroundResource(R.color.success);

        in_addressDelivery = delivery;
        in_addressCity = city;
        in_addressState = state;
        in_addressZip = state;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.checkout_fragmentFrame, paymentFragment)
                .commit();

    }

    @Override
    public void onPaymentSent(String cardNumber, String cardExpiration, String cardCode) {
        imageView_reviewStep.setBackgroundResource(R.drawable.baseline_radio_button_checked_24);
        view_paymentLine.setBackgroundResource(R.color.success);

        in_cardNumber = cardNumber;
        in_cardDate = cardExpiration;
        in_cardCode = cardCode;


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.checkout_fragmentFrame, reviewFragment)
                .commit();

    }

    @Override
    public void onConfirmation() {

        OrderController.placeOrder(SessionController.getInstance(),
                in_addressDelivery, in_addressCity, in_addressState, in_addressZip,
                in_cardNumber, in_cardDate, in_cardCode, CartController.getCartItems());

        Intent intent = new Intent(this, DashBoardActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}