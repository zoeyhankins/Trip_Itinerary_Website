package com.example.tripitinerary.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripitinerary.R;
import com.google.android.material.snackbar.Snackbar;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;

    private static final String STORED_PASSWORD_HASH = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (username.equals("Trip User") && sha256(password).equals(STORED_PASSWORD_HASH)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Snackbar.make(findViewById(R.id.coordinatorLayout), "Invalid credentials", Snackbar.LENGTH_SHORT).show();
        }
    }

    private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}