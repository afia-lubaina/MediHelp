package com.example.medihelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, confirmPassword,name;
    private  Button buttonSignup;
    private Button buttonSign_in;
    private FirebaseAuth mAuth;
    private ProgressDialog progress_signup;



    @Override
       protected void onCreate(Bundle savedInstanceState) {

            //init firebase
            mAuth=FirebaseAuth.getInstance();

            progress_signup=new ProgressDialog(this);
            progress_signup.setTitle("Please wait");
            progress_signup.setCanceledOnTouchOutside(false);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);
            name=findViewById(R.id.username);
            editTextEmail = findViewById(R.id.email);
            editTextPassword = findViewById(R.id.password);
            confirmPassword = findViewById(R.id.confirmpassword);
            buttonSignup = findViewById(R.id.signup);
            buttonSign_in = findViewById(R.id.signin);
            buttonSign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
            });

            buttonSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    validateData();
                }
            });

        }

        private String username="", email="",password="", ConfirmPassword;
        private void validateData(){
            email=editTextEmail.getText().toString().trim();
            password=editTextPassword.getText().toString().trim();
            username=name.getText().toString().trim();
            ConfirmPassword=confirmPassword.getText().toString().trim();

            //validate data

            if(TextUtils.isEmpty(username)){
                Toast.makeText(this,"Enter Username",Toast.LENGTH_SHORT).show();
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(ConfirmPassword)){
                Toast.makeText(this,"Confirm Password",Toast.LENGTH_SHORT).show();
            }
            else if(!password.equals(ConfirmPassword)){
                Toast.makeText(this,"Passwords Doesn't Match",Toast.LENGTH_SHORT).show();
            }

            else{
                createUserAccount();
            }


        }

        private void createUserAccount(){
            progress_signup.setMessage("Creating Account...");
            progress_signup.show();
            mAuth.createUserWithEmailAndPassword(email,password).
                    addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progress_signup.dismiss();
                            updateUserInfo();
                            }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progress_signup.dismiss();
                            Toast.makeText(SignUp.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

         }

    private void updateUserInfo() {
        progress_signup.setMessage("Saving User Info...");
        long timestamp = System.currentTimeMillis();

        String uid = mAuth.getUid();

        // Setup data to add in db

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("email", email);
        hashMap.put("name", username);
        hashMap.put("profileImage", "");
        hashMap.put("userType", "user");
        hashMap.put("timestamp", timestamp);

        // Setup data to db

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progress_signup.dismiss();
                        Toast.makeText(SignUp.this, "Account Created...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progress_signup.dismiss();
                        Toast.makeText(SignUp.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseDBError", "Error updating user info: " + e.getMessage(), e);
                    }
                });
    }

}