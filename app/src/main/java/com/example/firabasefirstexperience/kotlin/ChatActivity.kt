package com.example.firabasefirstexperience.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.text.method.TextKeyListener.clear

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.widget.*

import com.bumptech.glide.Glide
import com.example.firabasefirstexperience.java.adapter.MessageAdapter
import com.example.firabasefirstexperience.java.model.Chat
import com.example.firabasefirstexperience.java.model.User
import com.example.firabasefirstexperience.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.util.ArrayList
import java.util.HashMap

import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    private var fb_User: FirebaseUser? = null
    private var reference: DatabaseReference? = null

    private var messageAdapter: MessageAdapter? = null
    private var listOfChats: List<Chat>? = null

    private var recView_Chats: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setSupportActionBar(toolBar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar?.setNavigationOnClickListener { finish() }

        recView_Chats = findViewById(R.id.recView_Chats)
        recView_Chats?.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        linearLayoutManager.stackFromEnd = true
        recView_Chats?.layoutManager = linearLayoutManager


        val intent = intent

        val userId = intent.getStringExtra("userId")

        but_Send?.setOnClickListener {
            val msg = et_Message.text.toString()
            if (msg.isNotEmpty()) {
                sendMessage(fb_User!!.uid, userId, msg)
            } else {
                Toast.makeText(this@ChatActivity, "You can't send an empty message", Toast.LENGTH_SHORT).show()
            }

            et_Message.text = null
        }

        tv_UserName?.setOnClickListener {
            val intent = Intent(this@ChatActivity, DiffProfileActivity::class.java)
            intent.putExtra("id", userId)
            startActivity(intent)
        }


        fb_User = FirebaseAuth.getInstance().currentUser

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

        reference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)

                if (user != null) {
                    tv_UserName.text = user.userName

                    if (user.imageURL == "default") {
                        profileImage_Chat.setImageResource(R.mipmap.ic_launcher_round)
                    } else {
                        Glide
                                .with(applicationContext)
                                .load(user.imageURL)
                                .into(profileImage_Chat)
                    }
                }

                if (user != null) {
                    readMessage(fb_User!!.uid, userId)
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }


    private fun sendMessage(sender: String, receiver: String, message: String) {

        val reference = FirebaseDatabase.getInstance().reference

        val hm_Messagee = HashMap<String, Any>()
        hm_Messagee["sender"] = sender
        hm_Messagee["receiver"] = receiver
        hm_Messagee["message"] = message


        reference.child("Chats").push().setValue(hm_Messagee)
    }

    private fun readMessage(myId: String, userId: String) {
        listOfChats = ArrayList()

        reference = FirebaseDatabase.getInstance().getReference("Chats")

        reference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                (listOfChats as ArrayList<Chat>).clear()

                for (snapshot in dataSnapshot.children) {
                    val chat = snapshot.getValue(Chat::class.java)

                    if (chat != null) {

                        if (chat.receiver == myId && chat.sender == userId || chat.receiver == userId && chat.sender == myId) {
                            (listOfChats as ArrayList<Chat>).add(chat)

                            messageAdapter = MessageAdapter(this@ChatActivity, listOfChats)
                            recView_Chats?.adapter = messageAdapter
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

}
