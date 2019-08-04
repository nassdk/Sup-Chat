package com.example.firabasefirstexperience.kotlin.presentation.chat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.kotlin.presentation.chat.MessageAdapter
import com.example.firabasefirstexperience.kotlin.model.Chat
import com.example.firabasefirstexperience.kotlin.model.User
import com.example.firabasefirstexperience.kotlin.presentation.chat.mvp.ChatActivityPresenter
import com.example.firabasefirstexperience.kotlin.presentation.chat.mvp.ChatActivityView
import com.example.firabasefirstexperience.kotlin.presentation.diffprofile.ui.DiffProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.ArrayList

class ChatActivity : MvpAppCompatActivity(), ChatActivityView {

    private lateinit var fbUser: FirebaseUser
    private lateinit var reference: DatabaseReference

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var listOfChat: List<Chat>

    private lateinit var recViewChats: RecyclerView

    @InjectPresenter
    lateinit var presenter: ChatActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        initViews()
    }


    private fun initViews() {
        setSupportActionBar(toolBar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar?.setNavigationOnClickListener { finish() }

        recViewChats = findViewById(R.id.recView_Chats)
        recViewChats.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        linearLayoutManager.stackFromEnd = true
        recView_Chats?.layoutManager = linearLayoutManager

        val intent = intent

        val userId = intent.getStringExtra("userId")

        fbUser = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

        but_Send.setOnClickListener {
            val msg = et_Message.text.toString()
            if (msg.isEmpty()) {
                presenter.onEmptyError()
            } else {
                presenter.sendMessage(fbUser.uid, userId, msg)
            }

            et_Message.text = null
        }

        tv_UserName.setOnClickListener {
            presenter.toDiffProfile()
        }

        reference.addValueEventListener(object : ValueEventListener {
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
                    presenter.readMessage(fbUser.uid, userId)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    override fun showEmptyError() {
        Toast.makeText(applicationContext, "You can't send an empty Message", Toast.LENGTH_SHORT).show()
    }

    override fun toDiffProfile() {
        val intent = intent
        val userId = intent.getStringExtra("userId")

        val intentToDiffProfile = Intent(applicationContext, DiffProfileActivity::class.java)
        intentToDiffProfile.putExtra("id", userId)
        startActivity(intentToDiffProfile)
    }

    override fun setAdapter(listOfChats: ArrayList<Chat>) {
        messageAdapter = MessageAdapter(listOfChats)
        recView_Chats?.adapter = messageAdapter
    }
}