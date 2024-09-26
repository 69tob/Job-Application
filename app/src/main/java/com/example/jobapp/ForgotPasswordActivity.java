package com.example.jobapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText forgotPasswordEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        forgotPasswordEmail = findViewById(R.id.forgotPasswordEmail);
        Button resetPasswordButton = findViewById(R.id.resetPasswordButton);
        TextView backToLoginText = findViewById(R.id.backToLoginText); // Corrected this to TextView

        // Set onClick listener for the back button to navigate to LoginActivity
        backToLoginText.setOnClickListener(view -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Ensure this activity is removed from the back stack
        });

        // Set onClick listener for the reset password button
        resetPasswordButton.setOnClickListener(view -> {
            String email = forgotPasswordEmail.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Attempt to send a password reset email
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Reset email sent.", Toast.LENGTH_SHORT).show();
                            // Optionally navigate back to the login screen after successful email send
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Ensure this activity is removed from the back stack
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Error sending reset email. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
