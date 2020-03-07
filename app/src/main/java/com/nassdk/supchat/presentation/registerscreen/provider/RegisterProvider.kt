package com.nassdk.supchat.presentation.registerscreen.provider

import android.content.Context
import android.widget.Toast
import com.nassdk.supchat.domain.model.User
import com.nassdk.supchat.presentation.registerscreen.mvp.RegisterPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RegisterProvider(var presenter: RegisterPresenter, var context: Context) {


    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var reference: DatabaseReference = database.getReference("Users")

    fun registerUser(textEmail: String, textPassword: String, textUserName: String, imageURL: String, status: String) {
        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "User Successfully Registered! Please Enter in your Profile", Toast.LENGTH_LONG).show()

                reference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val firebaseUser = auth.currentUser
                        val id = firebaseUser!!.uid
                        updateUser(id, textUserName, textPassword, textEmail, imageURL, status)

                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

            } else {
                Toast.makeText(context, "Please use different email address or try later", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateUser(id: String, textUserName: String, textPassword: String, textEmail: String, imageURL: String, status: String) {
    reference = FirebaseDatabase.getInstance().getReference("Users").child(id)
    val user = User(id, textUserName, textPassword, textEmail, imageURL, status)
    reference.setValue(user)
}
}