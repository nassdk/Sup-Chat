package com.example.data.firebaseservice

import com.google.firebase.database.FirebaseDatabase

class ChatService {

    fun sendMessage(senderId: String, receiverId: String, message: String) {
        val reference = FirebaseDatabase.getInstance().reference
        reference.child("Chats").push().setValue(
                mapOf(
                        "sender" to senderId,
                        "receiver" to receiverId,
                        "message" to message
                )
        )
    }
}