package com.nassdk.supchat.presentation.registerscreen.ui

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.presentation.registerscreen.mvp.RegisterPresenter
import com.nassdk.supchat.presentation.registerscreen.mvp.RegisterView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register_acitivty.*
import kotlinx.android.synthetic.main.bar_layout.*

class RegisterActivity : MvpAppCompatActivity(), RegisterView {

    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    @InjectPresenter
    lateinit var registerPresenter: RegisterPresenter

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
                if (registerPresenter.checkInternetConnection(context = applicationContext)) {
                } else {
                    registerPresenter.register(textEmail, textPassword, textUserName, imageURL, status, this@RegisterActivity)
                    finish()
                }
            }
        }
    }

    override fun showEmptyError() {
        Toast.makeText(this@RegisterActivity, "All fields must be field", Toast.LENGTH_SHORT).show()
    }

    override fun showPassError() {
        Toast.makeText(this@RegisterActivity, "Password must consist of at least of 8 characters", Toast.LENGTH_SHORT).show()
    }

    override fun showDialog() {
        val builder = AlertDialog.Builder(this@RegisterActivity)
        builder.setTitle("Warning!")
                .setMessage("Your device is not connected to Internet. Please, try later")
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setNegativeButton("Exit"
                ) { _, _ -> finish() }
        val alert = builder.create()
        alert.show()
    }

}