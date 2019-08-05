package com.nassdk.supchat.presentation.resetpassword.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.presentation.login.ui.LoginActivity
import com.nassdk.supchat.presentation.resetpassword.mvp.ResetPassActivityPresenter
import com.nassdk.supchat.presentation.resetpassword.mvp.ResetPassActivityView
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
            if (presenter.checkInternetConnection(context = applicationContext)) {
            } else {
                presenter.resetPassword(etEmailReset.text.toString())
            }
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

    override fun showDialog() {
        val builder = AlertDialog.Builder(this@ResetPassActivity)
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