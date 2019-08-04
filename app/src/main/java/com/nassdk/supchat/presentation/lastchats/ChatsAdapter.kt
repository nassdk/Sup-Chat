package com.nassdk.supchat.presentation.lastchats

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nassdk.supchat.R
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

        private val tv_Name: TextView = itemView.findViewById(R.id.tv_Name)
        private val userImage: CircleImageView = itemView.findViewById(R.id.userImage)
        private val civ_StatusOn: CircleImageView = itemView.findViewById(R.id.civStatus_On)
        private val civ_Status_Off: CircleImageView = itemView.findViewById(R.id.civStatus_Off)

        fun bind(user: User) {
            tv_Name.text = user.userName

            if (user.imageURL == "default") {
                userImage.setImageResource(R.mipmap.ic_launcher_round)
            } else {
                Glide
                        .with(itemView.context)
                        .load(user.imageURL)
                        .into(userImage)
            }

            if (user.status == "online") {
                civ_StatusOn.visibility = View.VISIBLE
                civ_Status_Off.visibility = View.GONE
            } else {
                civ_StatusOn.visibility = View.GONE
                civ_Status_Off.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatActivity::class.java)
                intent.putExtra("userId", user.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}