package com.nassdk.supchat.presentation.diffprofile.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.nassdk.supchat.R
import com.nassdk.supchat.model.User
import com.nassdk.supchat.presentation.chat.ui.ChatActivity
import com.nassdk.supchat.presentation.diffprofile.mvp.DiffProfilePresenter
import com.nassdk.supchat.presentation.diffprofile.mvp.DiffProfileView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_diff_profile.*

class DiffProfileActivity : MvpAppCompatActivity(), DiffProfileView, View.OnClickListener {

    private lateinit var id: String

    @InjectPresenter
    lateinit var presenter: DiffProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_profile)

        initViews()
    }

    private fun initViews() {
        val intent = intent
        id = intent.getStringExtra("id")

        val reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference("Users").child(id)

        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val userUnit = p0.getValue(User::class.java)

                if (userUnit != null) {
                    tvDifProfile_Name.text = userUnit.userName

                    if (!userUnit.status.equals("offline")) {
                        tvDifProfile_Status.visibility = View.VISIBLE
                    } else {
                        tvDifProfile_Status.visibility = View.GONE
                    }

                    if (userUnit.imageURL == "default") {
                        ivDifProfile_Image.setImageResource(R.mipmap.ic_launcher_round)
                    } else {
                        Glide
                                .with(applicationContext)
                                .load(userUnit.imageURL)
                                .into(ivDifProfile_Image)
                    }
                }
            }

        })

        ibDifProfile_back.setOnClickListener(this)
        fab_message.setOnClickListener(this)
    }

    override fun openChat() {
        val intentToChatActivity = Intent(applicationContext, ChatActivity::class.java)
        intentToChatActivity.putExtra("userId", id)
        startActivity(intentToChatActivity)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ibDifProfile_back -> finish()
            R.id.fab_message -> {
                if (presenter.checkInternetConnection(context = applicationContext)) {
                } else {
                    presenter.toChat()
                }
            }
        }
    }

    override fun showDialog() {
        val builder = AlertDialog.Builder(this@DiffProfileActivity)
        builder.setTitle("Warning!")
                .setMessage("Your device is not connected to Internet. Please, try later")
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setNegativeButton("Exit"
                ) { _, _ -> finish() }
        val alert = builder.create()
        alert.show()
    }
}