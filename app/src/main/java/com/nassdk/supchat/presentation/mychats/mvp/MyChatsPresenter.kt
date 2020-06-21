package com.nassdk.supchat.presentation.mychats.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.domain.model.Chat
import com.example.domain.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.nassdk.supchat.global.BasePresenter
import com.nassdk.supchat.global.navigation.Screens
import java.util.*

@InjectViewState
class MyChatsPresenter : BasePresenter<MyChatsView>() {

    private var mList = listOf<User>()
    private var usersList = arrayListOf<String>()

    override fun onFirstViewAttach() = setUpChats()

    private fun setUpChats() {

        usersList = ArrayList()

        viewState.showLoading()
        referenceChats.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                usersList.clear()

                for (snapshot in p0.children) {

                    val chat = snapshot.getValue(Chat::class.java)
                    if (chat != null) {

                        if (chat.sender == fbUser?.uid && (!usersList.contains(chat.receiver))) {
                            usersList.add(chat.receiver)
                        }

                        if (chat.receiver == fbUser?.uid && (!usersList.contains(chat.sender))) {
                            usersList.add(chat.sender)
                        }
                    }

                }

                readChats(usersList)
                viewState.hideLoading()
            }

        })
    }

    private fun readChats(usersList: ArrayList<String>) {

        mList = ArrayList()
        referenceUsers.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                (mList as ArrayList<User>).clear()

                for (snapshot in p0.children) {
                    val user = snapshot.getValue(User::class.java)

                    for (id in (usersList)) {
                        if (user?.id == id) {
                            if (mList.isNotEmpty()) {
                                for (user1 in mList as ArrayList<User>) {
                                    if (user.id != user1.id) {
                                        (mList as ArrayList<User>).add(user)
                                        break
                                    }
                                }
                            } else {
                                (mList as ArrayList<User>).add(user)
                            }
                        }
                    }
                }

                viewState.setAdapter(mList as ArrayList<User>)
            }

        })
    }

    fun toConversationWith(userId: String) = router.navigateTo(Screens.ConversationScreen(userId = userId))

    fun toSearch() = router.navigateTo(Screens.ChatsScreen)
}