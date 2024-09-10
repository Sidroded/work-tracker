package com.sidroded.worktracker.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.sidroded.worktracker.MainActivity;
import com.sidroded.worktracker.R;

public class SignInActivity extends AppCompatActivity {
    String TAG = "Work Tracker";
    EditText emailEditText;
    EditText passwordEditText;
    EditText repeatPasswordEditText;
    Button signInButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.edit_text_email_sign_in_activity);
        passwordEditText = findViewById(R.id.edit_text_password_sign_in_activity);
        repeatPasswordEditText = findViewById(R.id.edit_text_repeat_password_sign_in_activity);
        signInButton = findViewById(R.id.button_sign_in_login_activity);

        signInButton.setOnClickListener(view -> {
            onSignInButtonClick();
        });
    }

    private void onSignInButtonClick() {
        if (fieldsValidation()) {
            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, R.string.something_wrong_toast, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean fieldsValidation() {
        if (emailEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty() || repeatPasswordEditText.getText().toString().isEmpty()) {
            Toast.makeText(SignInActivity.this, R.string.fill_all_fields_toast, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!passwordEditText.getText().toString().equals(repeatPasswordEditText.getText().toString())) {
            Toast.makeText(SignInActivity.this, R.string.password_mismatch_toast, Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordEditText.getText().toString().length() < 6) {
            Toast.makeText(SignInActivity.this, R.string.password_symbols_validation_toast, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!emailEditText.getText().toString().contains("@")) {
            Toast.makeText(SignInActivity.this, R.string.something_wrong_toast, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
