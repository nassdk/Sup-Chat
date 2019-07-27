package com.example.firabasefirstexperience.java.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.firabasefirstexperience.R;
import com.example.firabasefirstexperience.java.adapter.UserAdapter;
import com.example.firabasefirstexperience.java.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recView_SearchUsers;
    private FirebaseUser fbUser;
    private List<User> listUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageButton ibSerach_back = findViewById(R.id.ibSearch_back);
        ibSerach_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Toolbar toolBarSearch = findViewById(R.id.toolBarSearch);
        toolBarSearch.setTitle("");
        setSupportActionBar(toolBarSearch);

        recView_SearchUsers = findViewById(R.id.recView_SearchUsers);
        recView_SearchUsers.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
        recView_SearchUsers.setHasFixedSize(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        fbUser = FirebaseAuth.getInstance().getCurrentUser();

        listUsers = new ArrayList<>();

        if (reference != null) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listUsers.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);

                        if (user != null && fbUser != null) {

                            if (!user.getId().equals(fbUser.getUid())) {
                                listUsers.add(user);
                            }
                        }
                    }
                    UserAdapter searchUsersAdapter = new UserAdapter(SearchActivity.this, listUsers);
                    recView_SearchUsers.setAdapter(searchUsersAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.item_search);
        SearchView sv_Users = (SearchView) item.getActionView();
        sv_Users.setIconified(false);
        sv_Users.setQueryHint("Search");


        sv_Users.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchUsers(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void searchUsers(String newText) {

        List<User> myList = new ArrayList<>();
        for (User user : listUsers) {
            if (user.getUserName().toLowerCase().contains(newText.toLowerCase())) {
                myList.add(user);
            }
        }

        UserAdapter adapterUserSearch = new UserAdapter(SearchActivity.this, myList);
        recView_SearchUsers.setAdapter(adapterUserSearch);
    }
}
