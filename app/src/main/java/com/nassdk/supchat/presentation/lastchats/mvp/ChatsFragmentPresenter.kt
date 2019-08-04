package com.nassdk.supchat.presentation.lastchats.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.nassdk.supchat.model.Chat
import com.nassdk.supchat.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.ArrayList

@InjectViewState
class ChatsFragmentPresenter : MvpPresenter<ChatsFragmentView>() {


    private var fbUser: FirebaseUser? = null
    private var reference: DatabaseReference? = null
    private var mList: List<User>? = null
    private lateinit var usersList: ArrayList<String>

    fun setUpChats() {

        usersList = ArrayList()
        fbUser = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Chats")

        viewState.showProgress()
        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                usersList.clear()

                for (snapshot in p0.children) {

                    val chat = snapshot.getValue(Chat::class.java)
                    if (chat != null) {

                        if (chat.sender == fbUser?.uid && (!usersList.contains(chat.receiver))) {
                            usersList.add(chat.receiver!!)
                        }

                        if (chat.receiver == fbUser?.uid && (!usersList.contains(chat.sender))) {
                            usersList.add(chat.sender!!)
                        }
                    }

                }

                readChats(usersList)
                viewState.hideProgress()
            }

        })
    }

    private fun readChats(usersList: ArrayList<String>) {

        mList = ArrayList()
        reference = FirebaseDatabase.getInstance().getReference("Users")
        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                (mList as ArrayList<User>).clear()

                for (snapshot in p0.children) {
                    val user = snapshot.getValue(User::class.java)

                    for (id in (usersList)) {
                        if (user?.id == id) {
                            if (mList?.size != 0) {
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

    fun toSearch() {
        viewState.openSearchUser()
    }

}