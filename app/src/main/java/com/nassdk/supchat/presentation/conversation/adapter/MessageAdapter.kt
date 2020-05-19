package com.nassdk.supchat.presentation.conversation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Chat
import com.nassdk.supchat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var data = arrayListOf<Chat>()

    fun setData(newData: ArrayList<Chat>) {
        data.run {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, postion: Int): ViewHolder {
        return if (postion == MSG_LEFT) {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_left, parent, false)
            ViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_right, parent, false)
            ViewHolder(view)
        }
    }

    override fun getItemCount() =  data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var tvShowMessage: TextView = itemView.findViewById(R.id.tv_showMessage)

        fun bind(chat: Chat) {
            tvShowMessage.text = chat.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val fbUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        return if (data[position].sender == (fbUser?.uid)) {
            MSG_RIGHT
        } else MSG_LEFT
    }

    companion object {
        const val MSG_LEFT = 0
        const val MSG_RIGHT = 1
    }
}