package com.example.firabasefirstexperience.kotlin.presentation.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.kotlin.model.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MessageAdapter(private var listChats: List<Chat>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var MSG_LEFT = 0
    private var MSG_RIGHT = 1

    override fun onCreateViewHolder(parent: ViewGroup, postion: Int): ViewHolder {
        if (postion == MSG_LEFT) {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_left, parent, false)
            return ViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_right, parent, false)
            return ViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return listChats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listChats[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var tv_showMessge: TextView = itemView.findViewById(R.id.tv_showMessage)

        fun bind(chat: Chat) {
            tv_showMessge.text = chat.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val fbUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        return if (listChats[position].sender == (fbUser?.uid)) {
            MSG_RIGHT
        } else MSG_LEFT

    }
}