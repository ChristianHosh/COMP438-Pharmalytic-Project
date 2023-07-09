package com.example.finalproject.models.formatter;

import android.text.Editable;
import android.text.TextWatcher;

public class CardDateFormatter implements TextWatcher {
    private static final char date = '/';

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        int pos = 0;
        while (pos < editable.length()) {
            if (date == editable.charAt(pos) && (((pos + 1) % 3) != 0 || pos + 1 == editable.length())) {
                editable.delete(pos, pos + 1);
            } else {
                pos++;
            }
        }

        pos = 2;
        while (pos < editable.length()) {
            final char c = editable.charAt(pos);
            // Only if its a digit where there should be a space we insert a space
            if ("0123456789".indexOf(c) >= 0) {
                editable.insert(pos, "" + date);
            }
            pos += 3;
        }
    }
}
