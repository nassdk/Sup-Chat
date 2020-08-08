package com.nassdk.supchat.presentation.conversation.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.domain.model.Chat
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nassdk.supchat.global.BasePresenter
import com.nassdk.supchat.global.navigation.Screens
import java.util.*

@InjectViewState
class ConversationPresenter : BasePresenter<ConversationView>() {

    fun sendMessage(sender: String, receiver: String, message: String) {

        val reference = FirebaseDatabase.getInstance().reference

        reference.child("Chats").push().setValue(hashMapOf(
                "sender" to sender,
                "receiver" to receiver,
                "message" to message
        ))
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

                            viewState.setupMessages(listOfChats)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun fetchData(userId: String) {

        val fbUser = FirebaseAuth.getInstance().currentUser!!
        val reference = FirebaseDatabase.getInstance().getReference("Users").child(userId)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)

                user?.let {
                    viewState.setData(user = it)
                    readMessage(fbUser.uid, userId)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun toUserProfileScreen(userId: String) = router.navigateTo(Screens.DiffProfileScreen(userId = userId))
}
