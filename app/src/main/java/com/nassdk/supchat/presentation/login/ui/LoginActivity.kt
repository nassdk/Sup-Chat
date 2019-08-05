package com.nassdk.supchat.presentation.login.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.presentation.login.mvp.LoginActivityPresenter
import com.nassdk.supchat.presentation.login.mvp.LoginActivityView
import com.nassdk.supchat.presentation.main.ui.MainActivity
import com.nassdk.supchat.presentation.resetpassword.ui.ResetPassActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.bar_layout.*

class LoginActivity : MvpAppCompatActivity(), LoginActivityView {

    @InjectPresenter
    lateinit var presenter: LoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
    }

    private fun initViews() {

        setSupportActionBar(tbForRegisterActivity)
        supportActionBar?.title = "Sup Chat"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        butLogIn.setOnClickListener {
            val textEmail = etEmail.text.toString()
            val textPassword = etPassword.text.toString()
            if (TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword)) {
                presenter.onEmptyError()
            } else {
                if (presenter.checkInternetConnection(context = applicationContext)) {
                } else {
                    presenter.userLog(textEmail, textPassword)
                }
            }
        }

        tvResetPassword.setOnClickListener {
            presenter.userResetPass()
        }
    }

    override fun showEmptyError() {
        Toast.makeText(applicationContext, "All fields must be filled!", Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess() {
        Toast.makeText(applicationContext, "Welcome to your Account", Toast.LENGTH_SHORT).show()
    }

    override fun showAuthError() {
        Toast.makeText(applicationContext, "Authentication failed. Wrong password or email", Toast.LENGTH_LONG).show()
    }

    override fun openResetPass() {
        startActivity(Intent(this@LoginActivity, ResetPassActivity::class.java))
    }

    override fun openMain() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

    override fun showDialog() {
        val builder = AlertDialog.Builder(this@LoginActivity)
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