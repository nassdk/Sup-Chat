package com.example.firabasefirstexperience.kotlin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.kotlin.model.User
import com.example.firabasefirstexperience.kotlin.activity.DiffProfileActivity
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapterkt(private var listUsers: List<User>) : RecyclerView.Adapter<UserAdapterkt.ViewHolder>() {
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

        private var tv_Name: TextView? = itemView.findViewById(R.id.tv_Name)
        private var userImage: CircleImageView? = itemView.findViewById(R.id.userImage)

        fun bind(user: User) {
            tv_Name?.text = user.userName

            if (user.imageURL == "default") {
                userImage?.setImageResource(R.mipmap.ic_launcher_round)
            } else {
                Glide
                        .with(itemView.context)
                        .load(user.imageURL)
                        .into(userImage!!)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DiffProfileActivity::class.java)
                intent.putExtra("id", user.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}