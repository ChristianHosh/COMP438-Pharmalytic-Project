package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.finalproject.models.formatter.CardDateFormatter;
import com.example.finalproject.models.formatter.CardNumberFormatter;

public class PaymentFragment extends Fragment {
    private EditText editText_cardNumber;
    private EditText editText_cardDate;
    private EditText editText_cardCode;
    private PaymentFragmentListener listener;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public interface PaymentFragmentListener {
        void onPaymentSent(String cardNumber, String cardExpiration, String cardCode);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        editText_cardNumber = rootView.findViewById(R.id.payment_et_card);
        editText_cardDate = rootView.findViewById(R.id.payment_et_expiration);
        editText_cardCode = rootView.findViewById(R.id.payment_et_code);
        AppCompatButton button_continueToReview = rootView.findViewById(R.id.payment_btn_toReview);

        editText_cardNumber.addTextChangedListener(new CardNumberFormatter());
        editText_cardDate.addTextChangedListener(new CardDateFormatter());

        button_continueToReview.setOnClickListener(e -> {
            String cardNumber = editText_cardNumber.getText().toString().trim();
            String cardDate = editText_cardDate.getText().toString().trim();
            String cardCode = editText_cardCode.getText().toString().trim();

            listener.onPaymentSent(cardNumber, cardDate, cardCode);
        });


        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PaymentFragmentListener) {
            listener = (PaymentFragmentListener) context;
        } else {
            throw new RuntimeException(context + " must implement PaymentFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}