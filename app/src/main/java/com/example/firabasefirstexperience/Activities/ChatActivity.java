package com.example.firabasefirstexperience.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firabasefirstexperience.Adapter.ChatAdapter;
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
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView tv_UserName;
    private ImageButton but_Send;
    private EditText et_Message;

    private FirebaseUser fb_User;
    private DatabaseReference reference;

    private Intent intent;

    private ChatAdapter chatAdapter;
    private List<Chat> listOfChats;

    private RecyclerView recView_Chats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        profileImage = findViewById(R.id.profileImage);
        tv_UserName = findViewById(R.id.tv_UserName);
        but_Send = findViewById(R.id.but_Send);
        et_Message = findViewById(R.id.et_Message);


        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recView_Chats = findViewById(R.id.recView_Chats);
        recView_Chats.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        recView_Chats.setLayoutManager(linearLayoutManager);


        intent = getIntent();

        final String userId = intent.getStringExtra("userId");

        but_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = et_Message.getText().toString();
                if (!msg.isEmpty()) {
                    sendMessage(fb_User.getUid(), userId, msg);
                } else {
                    Toast.makeText(ChatActivity.this, "You can't send an empty message", Toast.LENGTH_SHORT).show();
                }

                et_Message.setText(null);

            }
        });


        fb_User = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                tv_UserName.setText(user.getUserName());

                readMessage(fb_User.getUid(), userId);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sendMessage(String sender, String receiver, String message) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hm_Messagee = new HashMap<>();
        hm_Messagee.put("sender", sender);
        hm_Messagee.put("receiver", receiver);
        hm_Messagee.put("message", message);


        reference.child("Chats").push().setValue(hm_Messagee);
    }

    private void readMessage(final String myId, final String userId) {
        listOfChats = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfChats.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat.getReceiver() != null) {

                        if ((chat.getReceiver().equals(myId) && chat.getSender().equals(userId)) || (chat.getReceiver().equals(userId) && chat.getSender().equals(myId))) {
                            listOfChats.add(chat);

                            chatAdapter = new ChatAdapter(ChatActivity.this, listOfChats);
                            recView_Chats.setAdapter(chatAdapter);
                        }
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
