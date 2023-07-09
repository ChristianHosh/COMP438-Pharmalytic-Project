package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddressFragment extends Fragment {
    private EditText editText_delivery;
    private EditText editText_city;
    private EditText editText_state;
    private EditText editText_zip;
    private AddressFragmentListener listener;


    public AddressFragment() {
        // Required empty public constructor
    }

    public interface AddressFragmentListener {
        void onAddressSent(String delivery, String city, String state, String zip);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_address, container, false);

        editText_delivery = rootView.findViewById(R.id.address_et_delivery);
        editText_city = rootView.findViewById(R.id.address_et_city);
        editText_state = rootView.findViewById(R.id.address_et_state);
        editText_zip = rootView.findViewById(R.id.address_et_zip);
        AppCompatButton button_continueToPayment = rootView.findViewById(R.id.address_btn_toPayment);

        button_continueToPayment.setOnClickListener(e -> {
            String delivery = editText_delivery.getText().toString().trim();
            String city = editText_city.getText().toString().trim();
            String state = editText_state.getText().toString().trim();
            String zip = editText_zip.getText().toString().trim();

            listener.onAddressSent(delivery, city, state, zip);

        });

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddressFragmentListener) {
            listener = (AddressFragmentListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddressFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}