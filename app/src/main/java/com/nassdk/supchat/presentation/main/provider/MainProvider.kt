package com.nassdk.supchat.presentation.main.provider

import com.nassdk.supchat.presentation.main.mvp.MainActivityPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class MainProvider(var presenter: MainActivityPresenter) {

    fun loadStatus(status: String) {
        val fbUser = FirebaseAuth.getInstance().currentUser
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(fbUser!!.uid)
        val statusMap = HashMap<String, Any>()
        statusMap["status"] = status

        reference.updateChildren(statusMap)
    }

}