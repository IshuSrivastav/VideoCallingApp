package com.example.videocallingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.SingleDateSelector;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText emailBox, passwordBox;
    Button loginBtn, singUpBtn;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Just a moment");

        emailBox = findViewById( R.id.emailBox);
        passwordBox = findViewById( R.id.passwordBox);
        loginBtn = findViewById(R.id.loginBtn);
        singUpBtn = findViewById(R.id.singUpBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String email, password;
                email = emailBox.getText().toString();
                password = passwordBox.getText().toString();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SingupActivity.class);
                startActivity(intent);
            }
        });
    }



}