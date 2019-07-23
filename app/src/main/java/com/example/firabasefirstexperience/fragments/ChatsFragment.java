package com.example.firabasefirstexperience.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.firabasefirstexperience.activity.SearchActivity;
import com.example.firabasefirstexperience.adapter.ChatsAdapter;
import com.example.firabasefirstexperience.model.Chat;
import com.example.firabasefirstexperience.model.User;
import com.example.firabasefirstexperience.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

    private RecyclerView recView_displayChats;
    private ChatsAdapter chatsAdapter;
    private List<User> mUsers;

    private FirebaseUser fuser;
    private DatabaseReference reference;

    private List<String> usersList;

    private ProgressBar spinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        spinner = view.findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);

        FloatingActionButton fab_newPen = view.findViewById(R.id.fab_newPen);
        recView_displayChats = view.findViewById(R.id.recView_displayChats);
        recView_displayChats.setHasFixedSize(true);
        recView_displayChats.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat != null) {

                        if (chat.getSender().equals(fuser.getUid()) && (!usersList.contains(chat.getReceiver()))) {
                            usersList.add(chat.getReceiver());
                        }

                        if (chat.getReceiver().equals(fuser.getUid()) && (!usersList.contains(chat.getSender()))) {
                            usersList.add(chat.getSender());
                        }
                    }
                }

                readChats();

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab_newPen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });

        return view;
    }

    private void readChats() {

        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    for (String id : usersList) {
                        if (user.getId().equals(id)) {
                            if (mUsers.size() != 0) {
                                for (User user1 : mUsers) {
                                    if (!user.getId().equals(user1.getId())) {
                                        mUsers.add(user);
                                        break;
                                    }
                                }
                            } else {
                                mUsers.add(user);
                            }
                        }
                    }
                }

                chatsAdapter = new ChatsAdapter(getContext(), mUsers);
                recView_displayChats.setAdapter(chatsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
