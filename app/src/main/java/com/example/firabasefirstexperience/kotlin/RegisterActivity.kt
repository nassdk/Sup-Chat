package com.example.firabasefirstexperience.kotlin

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.java.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register_acitivty.*
import kotlinx.android.synthetic.main.bar_layout.*
import kotlinx.android.synthetic.main.bar_layout.view.*
import kotlinx.android.synthetic.main.bar_layout.view.tbForRegisterActivity

class RegisterActivity : AppCompatActivity(), View.OnClickListener {


    private var auth: FirebaseAuth? = null
    private var reference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_acitivty)


        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        reference = database.getReference("Users")

        setSupportActionBar(tbForRegisterActivity)
        supportActionBar?.title = "Sup Chat"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        butSingUp.setOnClickListener(this)
    }


    override fun onClick(v: View) {

        val id = v.id

        when (id) {
            R.id.butSingUp -> {
                val textUserName = etUserName.text.toString().trim { it <= ' ' }
                val textPassword = etPassword.text.toString()
                val textEmail = etEmail.text.toString().trim { it <= ' ' }
                val imageURL = "default"
                val status = "offline"


                if (TextUtils.isEmpty(textUserName) || TextUtils.isEmpty(textPassword) || TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(this@RegisterActivity, "All fields must be filled", Toast.LENGTH_SHORT).show()
                } else if (textPassword.length < 8) {
                    Toast.makeText(this@RegisterActivity, "Password must constist at least of 8 characters", Toast.LENGTH_SHORT).show()
                } else {

                    register(textEmail, textPassword, textUserName, imageURL, status)
                }
            }
        }
    }

    private fun register(textEmail: String, textPassword: String, textUserName: String, imageURL: String, status: String) {
        auth?.createUserWithEmailAndPassword(textEmail, textPassword)?.addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(applicationContext, "User Successfully Registered", Toast.LENGTH_SHORT).show()

                reference?.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val firebaseUser = auth!!.currentUser

                        val id = firebaseUser!!.uid

                        updateUser(id, textUserName, textPassword, textEmail, imageURL, status)

                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

                finish()
        }
            else {
                Toast.makeText(applicationContext, "An error occurred, please try later", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateUser(id: String, textUserName: String, textPassword: String, textEmail: String, imageURL: String, status: String) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val user = User(id, textUserName, textPassword, textEmail, imageURL, status)
        reference?.setValue(user)
    }
}