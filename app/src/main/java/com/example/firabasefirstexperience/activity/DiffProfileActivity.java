package com.example.firabasefirstexperience.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.firabasefirstexperience.R;
import com.example.firabasefirstexperience.model.User;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiffProfileActivity extends AppCompatActivity {

    private TextView tvDifProfile_Name;
    private ImageButton ibDifProfile_back;
    private CircleImageView ivDifProfile_Image;
    private FloatingActionButton fab_message;

    private FirebaseUser fbUser;
    private DatabaseReference reference;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff_profile);

        tvDifProfile_Name = findViewById(R.id.tvDifProfile_Name);
        ibDifProfile_back = findViewById(R.id.ibDifProfile_back);
        fab_message = findViewById(R.id.fab_message);

        intent = getIntent();
        String id = intent.getStringExtra("id");

        ibDifProfile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final User user = dataSnapshot.getValue(User.class);
                tvDifProfile_Name.setText(user.getUserName());



                fab_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DiffProfileActivity.this, ChatActivity.class);
                        intent.putExtra("userId", user.getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
