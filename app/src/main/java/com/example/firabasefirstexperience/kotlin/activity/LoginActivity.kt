package com.example.firabasefirstexperience.kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firabasefirstexperience.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.bar_layout.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        setSupportActionBar(tbForRegisterActivity)
        supportActionBar?.title = "Sup Chat"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val fbUser: FirebaseUser? = null

        if (fbUser != null) {
            val intent = Intent(this@LoginActivity, StartActivity::class.java)
        }

        butLogIn.setOnClickListener {
            val textEmail = etEmail.text.toString()
            val textPassword = etPassword.text.toString()

            if (TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword)) {
                Toast.makeText(applicationContext, "All fields must be filled", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(textEmail, textPassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this@LoginActivity, "Welcome to your Account!", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }

        tvResetPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ResetPassActivity::class.java)
            startActivity(intent)
        }


    }
}
