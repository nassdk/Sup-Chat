package com.nassdk.supchat.presentation.diffprofile.mvp

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.database.*
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.domain.model.User

@InjectViewState
class DiffProfilePresenter : MvpPresenter<DiffProfileView>() {

    fun toChat() = viewState.openChat()

    fun fetchData(userId: String) {

        val reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference("Users").child(userId)

        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val userUnit = p0.getValue(User::class.java)

                if (userUnit != null) {
                    viewState.showData(user = userUnit)
                }
            }
        })
    }
}