package com.example.firabasefirstexperience.kotlin.presentation.registerscreen.ui

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.kotlin.presentation.registerscreen.mvp.RegisterActivityPresenter
import com.example.firabasefirstexperience.kotlin.presentation.registerscreen.mvp.RegisterActivityView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register_acitivty.*
import kotlinx.android.synthetic.main.bar_layout.*

class RegisterActivity : MvpAppCompatActivity(), RegisterActivityView {

    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    @InjectPresenter
    lateinit var registerPresenter: RegisterActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_acitivty)

        initViews()
    }

    private fun initViews() {
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        reference = database.getReference("Users")

        setSupportActionBar(tbForRegisterActivity)
        supportActionBar?.title = "Sup Chat"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        butSingUp.setOnClickListener {
            val textUserName = etUserName.text.toString().trim { it <= ' ' }
            val textPassword = etPassword.text.toString()
            val textEmail = etEmail.text.toString().trim { it <= ' ' }
            val imageURL = "default"
            val status = "offline"


            if (TextUtils.isEmpty(textUserName) || TextUtils.isEmpty(textPassword) || TextUtils.isEmpty(textEmail)) {
                registerPresenter.onEmptyError()
            } else if (textPassword.length < 8) {
                registerPresenter.onPassError()
            } else {
                registerPresenter.register(textEmail, textPassword, textUserName, imageURL, status, this@RegisterActivity)
                finish()
            }
        }
    }

    override fun showEmptyError() {
        Toast.makeText(this@RegisterActivity, "All fields must be field", Toast.LENGTH_SHORT).show()
    }

    override fun showPassError() {
        Toast.makeText(this@RegisterActivity, "Password must consist of at least of 8 characters", Toast.LENGTH_SHORT).show()
    }

}