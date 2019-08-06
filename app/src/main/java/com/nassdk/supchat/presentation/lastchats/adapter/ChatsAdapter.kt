package com.nassdk.supchat.presentation.lastchats.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.nassdk.supchat.R
import com.nassdk.supchat.model.Chat
import com.nassdk.supchat.model.User
import com.nassdk.supchat.presentation.chat.ui.ChatActivity
import de.hdodenhof.circleimageview.CircleImageView


class ChatsAdapter(private val listUsers: List<User>) : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userName: TextView = itemView.findViewById(R.id.tv_Name)
        private val userImage: CircleImageView = itemView.findViewById(R.id.userImage)
        private val imageStatusOn: CircleImageView = itemView.findViewById(R.id.civStatus_On)
        private val lastMessage: TextView = itemView.findViewById(R.id.tvLastMessage)

        fun bind(user: User) {

            lastMessageDisplay(user.id!!, lastMessage)

            userName.text = user.userName

            if (user.imageURL == "default") {
                userImage.setImageResource(R.mipmap.ic_launcher_round)
            } else {
                Glide
                        .with(itemView.context)
                        .load(user.imageURL)
                        .into(userImage)
            }

            if (user.status == "online") {
                imageStatusOn.visibility = View.VISIBLE
            } else {
                imageStatusOn.visibility = View.GONE
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatActivity::class.java)
                intent.putExtra("userId", user.id)
                itemView.context.startActivity(intent)
            }
        }


        private fun lastMessageDisplay(userId: String, lastMsg: TextView) {

            lateinit var message: String
            val fireBaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
            val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chats")

            reference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {

                    for (snapshot in p0.children) {
                        val chat = snapshot.getValue(Chat::class.java)

                        if (((chat?.receiver!! == fireBaseUser.uid) && (chat.sender == userId)) || (chat.receiver == userId && chat.sender!! == fireBaseUser.uid)) {
                            message = chat.message.toString()
                            lastMsg.text = message
                        }
                    }
                }
            })
        }
    }
}