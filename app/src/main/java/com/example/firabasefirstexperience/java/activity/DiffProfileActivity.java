package com.example.firabasefirstexperience.java.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firabasefirstexperience.R;
import com.example.firabasefirstexperience.java.model.User;
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
    private CircleImageView ivDifProfile_Image;
    private FloatingActionButton fab_message;
    private TextView tvDifProfile_Status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvDifProfile_Name = findViewById(R.id.tvDifProfile_Name);
        ImageButton ibDifProfile_back = findViewById(R.id.ibDifProfile_back);
        tvDifProfile_Status = findViewById(R.id.tvDifProfile_Status);
        fab_message = findViewById(R.id.fab_message);
        ivDifProfile_Image = findViewById(R.id.ivDifProfile_Image);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        ibDifProfile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    tvDifProfile_Name.setText(user.getUserName());

                    if (!user.getStatus().equals("offline")) {
                        tvDifProfile_Status.setVisibility(View.VISIBLE);
                    } else {
                        tvDifProfile_Status.setVisibility(View.GONE);
                    }

                    if (user.getImageURL().equals("default")) {
                        ivDifProfile_Image.setImageResource(R.mipmap.ic_launcher_round);
                    } else {
                        Glide
                                .with(getApplicationContext())
                                .load(user.getImageURL())
                                .into(ivDifProfile_Image);
                    }
                }


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
