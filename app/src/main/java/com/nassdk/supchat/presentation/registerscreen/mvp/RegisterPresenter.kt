package com.nassdk.supchat.presentation.registerscreen.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nassdk.supchat.domain.model.User

@InjectViewState
class RegisterPresenter : MvpPresenter<RegisterView>() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var reference: DatabaseReference = database.getReference("Users")

    fun registerUser(textEmail: String, textPassword: String, textUserName: String) {
        when {
            textEmail.isEmpty() -> viewState.showEmptyError()
            textPassword.isEmpty() -> viewState.showEmptyError()
            textUserName.isEmpty() -> viewState.showEmptyError()
            textPassword.count() < 8 -> viewState.showPassError()
            else -> {
                val auth: FirebaseAuth = FirebaseAuth.getInstance()

                auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewState.showSuccessMessage()
                        reference.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val firebaseUser = auth.currentUser
                                val id = firebaseUser!!.uid

                                updateUser(
                                        id = id,
                                        textUserName = textUserName,
                                        textPassword = textPassword,
                                        textEmail = textEmail,
                                        imageURL = "",
                                        status = ""
                                )
                            }

                            override fun onCancelled(databaseError: DatabaseError) {}
                        })

                    } else viewState.showRegisterError()
                }
            }
        }
    }

    private fun updateUser(id: String, textUserName: String, textPassword: String, textEmail: String, imageURL: String, status: String) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val user = User(id, textUserName, textPassword, textEmail, imageURL, status)
        reference.setValue(user)
    }
}