package com.nassdk.supchat.presentation.startscreen.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.presentation.login.ui.LoginActivity
import com.nassdk.supchat.presentation.registerscreen.ui.RegisterActivity
import com.nassdk.supchat.presentation.startscreen.mvp.StartActivityPresenter
import com.nassdk.supchat.presentation.startscreen.mvp.StartActivityView
import kotlinx.android.synthetic.main.start_main.*

class StartActivity : MvpAppCompatActivity(), StartActivityView, View.OnClickListener {


    @InjectPresenter
    lateinit var presenter: StartActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_main)

        butLog.setOnClickListener(this)
        butRegister.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.butLog -> {
                if (presenter.checkInternetConnection(applicationContext)) {
                } else {
                    presenter.toLoginActivity()
                }
            }
            R.id.butRegister -> {
                if (presenter.checkInternetConnection(context = applicationContext)) {
                } else {
                    presenter.toRegisterActivity()
                }
            }

        }
    }

    override fun openRegister() {
        startActivity(Intent(this@StartActivity, RegisterActivity::class.java))
    }

    override fun openLogin() {
        startActivity(Intent(this@StartActivity, LoginActivity::class.java))
    }

    override fun showDialog() {
        val builder = AlertDialog.Builder(this@StartActivity)
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