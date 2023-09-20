package com.example.medihelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    private Button reset, back;
    private EditText txtEmail;
    private String email;
    private FirebaseAuth auth;
    ProgressDialog progress_forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.email);
        reset = findViewById(R.id.reset);
        back = findViewById(R.id.backLogin);

        // Initialize ProgressDialog
        progress_forgotPassword = new ProgressDialog(this);
        progress_forgotPassword.setMessage("Sending reset email...");

        // Reset Button Listener
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtEmail.getText().toString().trim();
                if (isValidEmail(email)) {
                    progress_forgotPassword.show();
                    ResetPassword();
                } else {
                    txtEmail.setError("Please enter a valid email address");
                }
            }
        });

        // Back Button Code
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void ResetPassword() {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progress_forgotPassword.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(forgotPassword.this, "Reset Password link has been sent to your registered Email", Toast.LENGTH_SHORT).show();
                        } else {
                            Exception e = task.getException();
                            if (e != null) {
                                Toast.makeText(forgotPassword.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}
