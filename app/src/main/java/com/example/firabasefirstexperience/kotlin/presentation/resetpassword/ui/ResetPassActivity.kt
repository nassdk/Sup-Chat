package com.example.firabasefirstexperience.kotlin.presentation.resetpassword.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.kotlin.presentation.login.ui.LoginActivity
import com.example.firabasefirstexperience.kotlin.presentation.resetpassword.mvp.ResetPassActivityPresenter
import com.example.firabasefirstexperience.kotlin.presentation.resetpassword.mvp.ResetPassActivityView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_pass.*
import kotlinx.android.synthetic.main.bar_layout.*

class ResetPassActivity : MvpAppCompatActivity(), ResetPassActivityView {

    private lateinit var auth: FirebaseAuth

    @InjectPresenter
    lateinit var presenter: ResetPassActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)

        initViews()
    }

    private fun initViews() {
        setSupportActionBar(tbForRegisterActivity)
        supportActionBar?.title = "Reset Password"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()

        butReset.setOnClickListener {
            presenter.resetPassword(etEmailReset.text.toString())
        }
    }

    override fun showSuccess() {
        Toast.makeText(applicationContext, "Message with instruction was send to your email. Please check it", Toast.LENGTH_LONG).show()
    }

    override fun showEmptyError() {
        Toast.makeText(applicationContext, "Please, write your email to reset your password", Toast.LENGTH_SHORT).show()
    }

    override fun openLogin() {
        startActivity(Intent(this@ResetPassActivity, LoginActivity::class.java))
    }

    override fun showError(error: String?) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()

    }
}