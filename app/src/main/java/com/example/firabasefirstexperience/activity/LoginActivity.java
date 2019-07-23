package com.example.firabasefirstexperience.activity;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firabasefirstexperience.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tvResetPassword;
    private EditText eMail, password;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.tbForRegisterActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sup Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (firebaseUser != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


        auth = FirebaseAuth.getInstance();

        tvResetPassword = (TextView) findViewById(R.id.tvResetPassword);

        eMail = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);

        Button butLogIn = (Button) findViewById(R.id.butLogIn);

        butLogIn.setOnClickListener(this);
        tvResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.butLogIn:
                String textEmail = eMail.getText().toString();
                String textPassword = password.getText().toString();

                if (TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(LoginActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(textEmail, textPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(LoginActivity.this, "Welcome to your Account!", Toast.LENGTH_SHORT).show();

                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;
            case R.id.tvResetPassword:
                Intent intent = new Intent(LoginActivity.this, ResetPassActivity.class);
                startActivity(intent);
                break;
        }
    }
}
