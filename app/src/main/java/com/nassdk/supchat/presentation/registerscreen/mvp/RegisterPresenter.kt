package com.nassdk.supchat.presentation.registerscreen.mvp

import com.arellomobile.mvp.InjectViewState
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nassdk.supchat.global.extensions.emailRegex
import com.nassdk.supchat.global.BasePresenter

@InjectViewState
class RegisterPresenter : BasePresenter<RegisterView>() {

    fun registerUser(textEmail: String, textPassword: String, textUserName: String) {
        when {
            textPassword.count() < 8      -> viewState.showPassError()
            textEmail.matches(emailRegex) -> viewState.showEmailRegexError()
            else                          ->
            {
                val auth: FirebaseAuth = FirebaseAuth.getInstance()

                auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewState.showSuccessMessage()
                        referenceUsers.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {

                                val firebaseUser= auth.currentUser
                                val id                = firebaseUser!!.uid

                                updateUser(
                                        id           = id,
                                        textUserName = textUserName,
                                        textPassword = textPassword,
                                        textEmail    = textEmail,
                                        imageURL     = "",
                                        status       = ""
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

        val reference = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val user  = User(id, textUserName, textPassword, textEmail, imageURL, status)
        reference.setValue(user)
    }
}