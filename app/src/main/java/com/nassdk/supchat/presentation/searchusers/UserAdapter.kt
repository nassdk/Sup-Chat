package com.nassdk.supchat.presentation.searchusers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nassdk.supchat.R
import com.nassdk.supchat.model.User
import com.nassdk.supchat.presentation.diffprofile.ui.DiffProfileActivity
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private var listUsers: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
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