package com.example.firabasefirstexperience.kotlin.presentation.login.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.kotlin.presentation.login.mvp.LoginActivityPresenter
import com.example.firabasefirstexperience.kotlin.presentation.login.mvp.LoginActivityView
import com.example.firabasefirstexperience.kotlin.presentation.main.ui.MainActivity
import com.example.firabasefirstexperience.kotlin.presentation.resetpassword.ui.ResetPassActivity
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
                presenter.userLog(textEmail, textPassword)
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
    }
}