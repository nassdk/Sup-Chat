package com.nassdk.supchat.presentation.chat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.nassdk.supchat.R
import com.nassdk.supchat.presentation.chat.adapter.MessageAdapter
import com.nassdk.supchat.domain.model.Chat
import com.nassdk.supchat.domain.model.User
import com.nassdk.supchat.presentation.chat.mvp.ConversationPresenter
import com.nassdk.supchat.presentation.chat.mvp.ConversationView
import com.nassdk.supchat.presentation.diffprofile.ui.DiffProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.ArrayList

class ConversationActivity : MvpAppCompatActivity(), ConversationView {

    private lateinit var fbUser: FirebaseUser
    private lateinit var reference: DatabaseReference

    private lateinit var messageAdapter: MessageAdapter

    @InjectPresenter
    lateinit var presenter: ConversationPresenter

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

        recViewChats.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        linearLayoutManager.stackFromEnd = true
        recViewChats.layoutManager = linearLayoutManager

        val intent = intent

        val userId = intent.getStringExtra("userId")

        fbUser = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

        but_Send.setOnClickListener {
            val msg = et_Message.text.toString()
            if (msg.isEmpty()) {
                presenter.onEmptyError()
            } else {
                if (!isNetworkAvailable(context = this@ConversationActivity)) {
                    showNoInternetDialog()
                } else {
                    presenter.sendMessage(fbUser.uid, userId, msg)
                }
            }

            et_Message.text = null
        }

        tv_UserName.setOnClickListener {
            presenter.toDiffProfile()
        }

        presenter.fetchData(userId = userId)
    }

    override fun showEmptyError() = Toast.makeText(this, "You can't send an empty Message", Toast.LENGTH_SHORT).show()


    override fun toDiffProfile() {
        val intent = intent
        val userId = intent.getStringExtra("userId")

        val intentToDiffProfile = Intent(this, DiffProfileActivity::class.java)
        intentToDiffProfile.putExtra("id", userId)
        startActivity(intentToDiffProfile)
    }

    override fun setAdapter(listOfChats: ArrayList<Chat>) {
        messageAdapter = MessageAdapter(listOfChats)
        recViewChats.adapter = messageAdapter
    }

    override fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(this@ConversationActivity)
        builder.setTitle("Warning!")
                .setMessage("Your device is not connected to Internet. Please, try later")
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setNegativeButton("Exit"
                ) { _, _ -> finish() }
        val alert = builder.create()
        alert.show()
    }

    override fun setData(user: User) {

        tv_UserName.text = user.userName

        if (user.imageURL == "default") {
            profileImage_Chat.setImageResource(R.mipmap.ic_launcher_round)
        } else {
            Glide
                    .with(this)
                    .load(user.imageURL)
                    .into(profileImage_Chat)
        }
    }
}
