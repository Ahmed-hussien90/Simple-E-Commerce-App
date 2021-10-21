package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
 FirebaseAuth fAuth;
 FirebaseDatabase fdb;
 DatabaseReference dbRefUname;
 private DatabaseReference dbRefEmail;
 Button btnSignIn;
 EditText username;
 EditText Email;
 EditText password;
 TextView signInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activtiy);

        fAuth = FirebaseAuth.getInstance();
        btnSignIn = findViewById(R.id.btnSignIn);
        username = findViewById(R.id.username);
        Email =findViewById(R.id.Email);
        password = findViewById(R.id.password);
        signInText = findViewById(R.id.signInText);
        fdb = FirebaseDatabase.getInstance();

        if(getIntent().getStringExtra("check").equals("signUp")) {
            signInText.setText("Sign Up");
            btnSignIn.setText("Sign Up");
            username.setVisibility(View.VISIBLE);
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("check").equals("signUp")) {
                    fAuth.createUserWithEmailAndPassword(Email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                dbRefUname = fdb.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("username");
                                dbRefUname.setValue(username.getText().toString());
                                dbRefEmail = fdb.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("email");
                                dbRefEmail.setValue(Email.getText().toString());
                                Toast.makeText(getBaseContext(), "register successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(getBaseContext(), "register failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    fAuth.signInWithEmailAndPassword(Email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(getBaseContext(), "Email or Password incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}