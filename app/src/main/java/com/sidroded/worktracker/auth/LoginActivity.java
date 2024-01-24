package com.sidroded.worktracker.auth;

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
    private final AuthService authService = new AuthService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailEditText = findViewById(R.id.edit_text_email_login_activity);
        passwordEditText = findViewById(R.id.edit_text_password_login_activity);
        loginButton = findViewById(R.id.button_login_login_activity);
        signInButton = findViewById(R.id.button_sign_in_login_activity);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginButtonClick(view);
            }
        });
    }

    private void onLoginButtonClick(View view) {
        if (authService.checkFillEditTextLoginActivity(LoginActivity.this, emailEditText, passwordEditText)) {
            auth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Невірний імейл або пароль", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
