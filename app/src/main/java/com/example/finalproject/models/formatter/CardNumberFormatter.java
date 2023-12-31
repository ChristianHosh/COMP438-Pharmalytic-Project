package com.example.finalproject.models.formatter;

import android.text.Editable;
import android.text.TextWatcher;

public class CardNumberFormatter implements TextWatcher {
    private static final char space = ' ';

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        // Remove all spacing char
        int pos = 0;
        while (pos < editable.length()) {
            if (space == editable.charAt(pos) && (((pos + 1) % 5) != 0 || pos + 1 == editable.length())) {
                editable.delete(pos, pos + 1);
            } else {
                pos++;
            }
        }

        // Insert char where needed.
        pos = 4;
        while (pos < editable.length()) {
            final char c = editable.charAt(pos);
            // Only if its a digit where there should be a space we insert a space
            if ("0123456789".indexOf(c) >= 0) {
                editable.insert(pos, "" + space);
            }
            pos += 5;
        }
    }
}
