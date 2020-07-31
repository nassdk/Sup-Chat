package com.nassdk.supchat.presentation.searchusers.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.User
import com.nassdk.supchat.R
import kotlinx.android.synthetic.main.search_user_item.view.*

class UserAdapter(
        private var onUserClicked: (String) -> Unit?
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var users = arrayListOf<User>()

    fun setData(users: ArrayList<User>) {

        this.users.run {
            clear()
            addAll(users)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            itemView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.search_user_item, parent, false),
            clickListener = onUserClicked
    )


    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }


    inner class ViewHolder(
            itemView: View,
            val clickListener: (String) -> Unit?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {

            itemView.tv_userName?.text = user.userName

            if (user.imageURL == "default") {
                itemView.civ_userImage?.setImageResource(R.mipmap.ic_launcher_round)
            } else {
                Glide
                        .with(itemView.context)
                        .load(user.imageURL)
                        .into(itemView.civ_userImage)
            }

            itemView.setOnClickListener { clickListener(user.id) }
        }
    }
}