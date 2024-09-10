package com.sidroded.worktracker.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sidroded.worktracker.MainActivity;
import com.sidroded.worktracker.R;
import com.sidroded.worktracker.databinding.LoginActivityBinding;

public class LoginActivity extends AppCompatActivity {
    private static final int RS_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";
    private FirebaseAuth auth;
    private LoginActivityBinding binding;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signInButton;
    private TextView signInWithGoogle;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailEditText = findViewById(R.id.edit_text_email_login_activity);
        passwordEditText = findViewById(R.id.edit_text_password_login_activity);
        loginButton = findViewById(R.id.button_login_login_activity);
        signInButton = findViewById(R.id.button_sign_in_login_activity);
        signInWithGoogle = findViewById(R.id.text_view_login_with_google);
        forgotPassword = findViewById(R.id.text_view_forgot_password_login_activity);

        loginButton.setOnClickListener(this::onLoginButtonClick);
        signInButton.setOnClickListener(this::onSignInButtonClick);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("636785617725-o35gbeso3uvtpfvjt0a506c1bvnuvrl2.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        auth = FirebaseAuth.getInstance();
        
        signInWithGoogle.setOnClickListener(this::onSignInWithGoogleButtonClick);
    }

    private void onSignInButtonClick(View view) {
        Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    private void onSignInWithGoogleButtonClick(View view) {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RS_SIGN_IN);
        Log.d(TAG, "onSignInWithGoogleButtonClick: begin Google Sign In");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: " + requestCode);

        if (requestCode == RS_SIGN_IN) {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d("LoginActivity", "Attempting to authenticate with Google Account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("LoginActivity", "Login successful");
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d("LoginActivity", "Login failed: " + task.getException().getMessage());
                            Toast.makeText(LoginActivity.this, "Login failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("LoginActivity", "Login failure: " + e.getMessage());
                        Toast.makeText(LoginActivity.this, "Login failure", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onLoginButtonClick(View view) {
        if (checkFillEditTextLoginActivity()) {
            auth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
