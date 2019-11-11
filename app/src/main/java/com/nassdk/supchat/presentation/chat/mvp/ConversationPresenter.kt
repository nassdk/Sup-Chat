package com.nassdk.supchat.presentation.chat.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nassdk.supchat.domain.model.Chat
import com.nassdk.supchat.domain.model.User
import com.nassdk.supchat.presentation.chat.provider.ConverstaionProvider
import java.util.*

@InjectViewState
class ConversationPresenter : MvpPresenter<ConversationView>() {

    fun toDiffProfile() = viewState.toDiffProfile()


    fun onEmptyError() = viewState.showEmptyError()


    fun sendMessage(sender: String, receiver: String, message: String) {
        ConverstaionProvider(presenter = this).updateMessage(sender = sender, receiver = receiver, message = message)
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

    fun fetchData(userId: String) {

        val fbUser = FirebaseAuth.getInstance().currentUser!!
        val reference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)

                if (user != null) {
                    viewState.setData(user = user)
                }

                if (user != null) {
                    readMessage(fbUser.uid, userId)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}
