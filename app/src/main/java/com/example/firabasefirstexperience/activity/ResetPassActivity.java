package com.example.firabasefirstexperience.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firabasefirstexperience.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {

    private EditText etEmailReset;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        etEmailReset = findViewById(R.id.etEmailReset);
        Button butReset = findViewById(R.id.butReset);


        Toolbar toolbar = findViewById(R.id.toolBarForResetPass);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        butReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eMail = etEmailReset.getText().toString();

                if (eMail.isEmpty()) {
                    Toast.makeText(ResetPassActivity.this, "Please, write your Email", Toast.LENGTH_SHORT).show();
                } else {
                    auth.sendPasswordResetEmail(eMail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPassActivity.this, "Message with instruction send to your Email. Please Check it", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ResetPassActivity.this, LoginActivity.class));
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(ResetPassActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
