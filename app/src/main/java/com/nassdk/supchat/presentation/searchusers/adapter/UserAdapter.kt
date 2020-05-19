package com.nassdk.supchat.presentation.searchusers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nassdk.supchat.R
import com.example.domain.model.User
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(
        private var listUsers: List<User>,
        private var clickListener: (String) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.search_user_item, parent, false)
        return ViewHolder(
                itemView = view,
                clickListener = clickListener
        )
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }


    class ViewHolder(
            itemView: View,
            val clickListener: (String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var tv_Name: TextView? = itemView.findViewById(R.id.tv_userName)
        private var userImage: CircleImageView? = itemView.findViewById(R.id.civ_userImage)

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

            itemView.setOnClickListener {clickListener (user.id ?: null!!)}
        }
    }
}