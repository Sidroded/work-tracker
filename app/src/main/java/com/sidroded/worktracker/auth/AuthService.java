package com.sidroded.worktracker.auth;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.sidroded.worktracker.R;

public class AuthService {

    public boolean checkFillEditTextLoginActivity(Context context, EditText loginEditText, EditText passwordEditText) {
        if (loginEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
            Toast.makeText(context, R.string.fill_all_fields_toast, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
