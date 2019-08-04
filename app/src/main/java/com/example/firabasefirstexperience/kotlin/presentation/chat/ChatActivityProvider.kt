package com.example.firabasefirstexperience.kotlin.presentation.chat

import com.example.firabasefirstexperience.kotlin.presentation.chat.mvp.ChatActivityPresenter
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap

class ChatActivityProvider(var presenter: ChatActivityPresenter) {
    fun updateMessage(sender: String, receiver: String, message: String) {
        val reference = FirebaseDatabase.getInstance().reference

        val hm_Messagee = HashMap<String, Any>()
        hm_Messagee["sender"] = sender
        hm_Messagee["receiver"] = receiver
        hm_Messagee["message"] = message


        reference.child("Chats").push().setValue(hm_Messagee)
    }
}