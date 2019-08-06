package com.nassdk.supchat.presentation.chat.mvp

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.nassdk.supchat.model.Chat
import com.nassdk.supchat.presentation.chat.provider.ChatActivityProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nassdk.supchat.network.isNetworkAvailable
import java.util.ArrayList

@InjectViewState
class ChatActivityPresenter : MvpPresenter<ChatActivityView>() {

    fun checkInternetConnection(context: Context) : Boolean {
        if(!isNetworkAvailable(context)) {
            viewState.showDialog()
            return true
        }
        return false
    }

    fun toDiffProfile() {
        viewState.toDiffProfile()
    }

    fun onEmptyError() {
        viewState.showEmptyError()
    }

    fun sendMessage(sender: String, receiver: String, message: String) {
        ChatActivityProvider(presenter = this).updateMessage(sender = sender, receiver = receiver, message = message)
    }

    fun readMessage(myId: String, userId: String) {
        val listOfChats = ArrayList<Chat>()
        val reference = FirebaseDatabase.getInstance().getReference("Chats")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfChats.clear()

                for (snapshot in dataSnapshot.children) {
                    val chat = snapshot.getValue(Chat::class.java)

                    if (chat != null) {

                        if (chat.receiver == myId && chat.sender == userId || chat.receiver == userId && chat.sender == myId) {
                            listOfChats.add(chat)

                            viewState.setAdapter(listOfChats)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}
