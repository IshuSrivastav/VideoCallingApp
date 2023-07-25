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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SingupActivity extends AppCompatActivity {
    EditText emailBox, passwordBox, nameBox;
    Button loginBtn, singUpBtn;
    FirebaseAuth auth;
    FirebaseFirestore database;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(SingupActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are Creating your Account");

        nameBox =   findViewById(R.id.nameBox);
        emailBox = findViewById( R.id.emailBox);
        passwordBox = findViewById( R.id.passwordBox);
        loginBtn = findViewById(R.id.loginBtn);
        singUpBtn = findViewById(R.id.singUpBtn);


        singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String email, pass, name;
                email = emailBox.getText().toString();
                pass = passwordBox.getText().toString();
                name = nameBox.getText().toString();

                User user = new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            database.collection("Users")
                                            .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            startActivity(new Intent(SingupActivity.this, LoginActivity.class));
                                            finish();
                                        }
                                    });
                            Toast.makeText(SingupActivity.this, "Account is Successfully Created...", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SingupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}