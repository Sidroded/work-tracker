package com.sidroded.worktracker.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sidroded.worktracker.MainActivity;
import com.sidroded.worktracker.R;

public class LoginActivity extends AppCompatActivity {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailEditText = findViewById(R.id.edit_text_email_login_activity);
        passwordEditText = findViewById(R.id.edit_text_password_login_activity);
        loginButton = findViewById(R.id.button_login_login_activity);
        signInButton = findViewById(R.id.button_sign_in_login_activity);

        loginButton.setOnClickListener(this::onLoginButtonClick);

        signInButton.setOnClickListener(this::onSignInButtonClick);
    }

    private void onSignInButtonClick(View view) {
        Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    private void onLoginButtonClick(View view) {
        if (checkFillEditTextLoginActivity()) {
            auth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.wrong_login_or_pass_toast, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean checkFillEditTextLoginActivity() {
        if (emailEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, R.string.fill_all_fields_toast, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
