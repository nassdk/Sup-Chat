package com.example.firabasefirstexperience.kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.java.activity.ChatActivity
import com.example.firabasefirstexperience.java.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_diff_profile.*

class DiffProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_profile)


        val intent = intent
        val id = intent.getStringExtra("id")

        ibDifProfile_back.setOnClickListener {
            finish()
        }

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference("Users").child(id)

        reference?.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)

                if(user != null) {
                    tvDifProfile_Name.text = user.userName

                    if(!user.status.equals("offline")) {
                        tvDifProfile_Status.visibility = View.VISIBLE
                    } else {
                        tvDifProfile_Status.visibility = View.GONE
                    }


                    if(user.imageURL == "default") {
                        ivDifProfile_Image.setImageResource(R.mipmap.ic_launcher_round)
                    } else {
                        Glide
                                .with(this@DiffProfileActivity)
                                .load(user.imageURL)
                                .into(ivDifProfile_Image)
                    }
                }
            }

        })

        fab_message.setOnClickListener {
            val intent = Intent(this@DiffProfileActivity, com.example.firabasefirstexperience.kotlin.ChatActivity::class.java)
            intent.putExtra("userId", id)
            startActivity(intent)
        }

    }

}