package com.example.firabasefirstexperience.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firabasefirstexperience.Adapter.UserAdapter;
import com.example.firabasefirstexperience.Model.Chat;
import com.example.firabasefirstexperience.Model.User;
import com.example.firabasefirstexperience.R;
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
    private UserAdapter userXadapter;

    private List<User> xUsers;

    private FirebaseUser fb_User;
    private DatabaseReference reference;

    private List<String> listOfUsers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recView_displayChats = view.findViewById(R.id.recView_displayChats);
        recView_displayChats.setHasFixedSize(true);
        recView_displayChats.setLayoutManager(new LinearLayoutManager(getContext()));

        fb_User = FirebaseAuth.getInstance().getCurrentUser();

        listOfUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listOfUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);


                    if (chat.getSender().equals(fb_User.getUid())) {
                        listOfUsers.add(chat.getReceiver());
                    }


                    if (chat.getReceiver().equals(fb_User.getUid())) {
                        listOfUsers.add(chat.getSender());
                    }

                }

                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void readChats() {
        xUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                xUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);

                    if (user.getId() != null) {
                        for (String id : listOfUsers) {
                            if (user.getId().equals(id)) {
                                if (xUsers.size() != 0) {
                                    for (User user1 : xUsers) {
                                        if (!user.getId().equals(user1.getId())) {
                                            xUsers.add(user);
                                        }
                                    }
                                } else {
                                    xUsers.add(user);
                                }
                            }
                        }
                    }
                }

                userXadapter = new UserAdapter(getContext(), xUsers);
                recView_displayChats.setAdapter(userXadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
