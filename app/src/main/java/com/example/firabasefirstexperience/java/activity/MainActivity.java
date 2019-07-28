package com.example.firabasefirstexperience.java.activity;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.firabasefirstexperience.kotlin.activity.StartActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.firabasefirstexperience.java.model.User;
import com.example.firabasefirstexperience.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CircleImageView navHeader_ivProfile;
    private TextView navHeader_tvUserName, navHeader_tvEmail;

    private DrawerLayout drawerLayout;

    private DatabaseReference reference;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbarChats = findViewById(R.id.toolBarChats);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());


        setSupportActionBar(toolbarChats);
        toolbarChats.inflateMenu(R.menu.menu_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbarChats, R.string.nav_drawer_open, R.string.nav_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView nView = findViewById(R.id.nav_view);
        nView.setNavigationItemSelectedListener(this);
        View header = nView.getHeaderView(0);
        navHeader_tvUserName = header.findViewById(R.id.navHeader_tvUserName);
        navHeader_tvEmail = header.findViewById(R.id.navHeader_tvEmail);
        navHeader_ivProfile = header.findViewById(R.id.navHeader_ivProfile);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    navHeader_tvUserName.setText(user.getUserName());
                    navHeader_tvEmail.setText(user.geteMail());

                    Glide
                            .with(MainActivity.this)
                            .load(user.getImageURL())
                            .into(navHeader_ivProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setCheckable(false);

        switch (menuItem.getItemId()) {
            case R.id.nav_Home:
                break;

            case R.id.nav_Profile:
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_Settings:
                break;

            case R.id.nav_LogOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish();

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void status(String status) {

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> statusMap = new HashMap<>();
        statusMap.put("status", status);

        reference.updateChildren(statusMap);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}


