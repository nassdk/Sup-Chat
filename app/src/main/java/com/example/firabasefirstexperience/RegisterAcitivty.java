package com.example.firabasefirstexperience;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firabasefirstexperience.Profile.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterAcitivty extends AppCompatActivity {

    private EditText etEmail, etPassword, etUserName;
    private Button butSignUp;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private FirebaseUser firebaseUser;

    private User user;


    private ProgressDialog progressDialog;

    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acitivty);

        progressDialog = new ProgressDialog(this);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUserName = (EditText) findViewById(R.id.etUserName);

        butSignUp = (Button) findViewById(R.id.butSingUp);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        auth = FirebaseAuth.getInstance();
        user = new User();

        Toolbar toolbar = findViewById(R.id.tbForRegisterActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sup Chat");


        butSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textUserName = etUserName.getText().toString();
                String textPassword = etPassword.getText().toString();
                String textEmail = etEmail.getText().toString();


                if (TextUtils.isEmpty(textUserName) || TextUtils.isEmpty(textPassword) || TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(RegisterAcitivty.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                } else if (textPassword.length() < 8) {
                    Toast.makeText(RegisterAcitivty.this, "Password must constist at least of 8 characters", Toast.LENGTH_SHORT).show();
                } else {

                    register(textEmail, textPassword);


                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                            getValue();
                            String userId = firebaseUser.getUid();
                            reference.child(userId).setValue(user);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }
        });


    }


    public void register(String eMail, String password) {

        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        auth.createUserWithEmailAndPassword(eMail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterAcitivty.this, "User successfully registered!", Toast.LENGTH_SHORT).show();

                            progressDialog.hide();

                            Log.d(TAG, "User successfully registered");

                            finish();
                        } else {
                            Log.d(TAG, "There is an error");
                        }
                    }
                });
    }

    private void getValue() {
        user.setUserName(etUserName.getText().toString());
        user.seteMail(etEmail.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setImageURL("default");
    }
}

