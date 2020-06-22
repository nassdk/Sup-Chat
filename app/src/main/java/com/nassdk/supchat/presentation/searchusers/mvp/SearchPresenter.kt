package com.nassdk.supchat.presentation.searchusers.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.nassdk.supchat.global.BasePresenter
import java.util.*

@InjectViewState
class SearchPresenter : BasePresenter<SearchView>() {

    override fun onFirstViewAttach() = displayUsers()

    private val usersModel = arrayListOf<User>()

    fun searchUser(searchQuery: String) {

        val resultUserList = ArrayList<User>()

        for (user in usersModel) {
            if (user.userName.toLowerCase(Locale.getDefault()).contains(searchQuery.toLowerCase(Locale.getDefault()))) {
                resultUserList.add(user)
            }
        }

        viewState.setData(usersList = resultUserList)
    }


    private fun displayUsers() {

        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference(USERS)
        val fbUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

        reference.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {

                for (snapshot in p0.children) {
                    val user = snapshot.getValue(User::class.java)

                    if (user?.id != fbUser.uid) {
                        user?.let { usersModel.add(it) }
                    }
                }

                viewState.setData(usersModel)
            }
        })
    }

    fun toProfileScreen(id: String) {}
}