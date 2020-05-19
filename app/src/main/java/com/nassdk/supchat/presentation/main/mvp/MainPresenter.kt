package com.nassdk.supchat.presentation.main.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nassdk.supchat.presentation.main.provider.MainProvider

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    fun toLogOut() = viewState.openStart()

    fun toProfile() = viewState.openProfile()

    fun updateStatus(status: String) = MainProvider(presenter = this).loadStatus(status)

    fun fetchData() {
        val fbUser = FirebaseAuth.getInstance().currentUser!!
        val reference = FirebaseDatabase.getInstance().getReference("Users").child(fbUser.uid)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)

                if (user != null) {
                    viewState.showData(user = user)
                }
            }
        })
    }
}