package com.example.firabasefirstexperience.kotlin.presentation.startscreen.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.kotlin.presentation.login.ui.LoginActivity
import com.example.firabasefirstexperience.kotlin.presentation.registerscreen.ui.RegisterActivity
import com.example.firabasefirstexperience.kotlin.presentation.startscreen.mvp.StartActivityPresenter
import com.example.firabasefirstexperience.kotlin.presentation.startscreen.mvp.StartActivityView
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
            R.id.butLog -> presenter.toLoginActivity()
            R.id.butRegister -> presenter.toRegisterActivity()
        }
    }

    override fun openRegister() {
        startActivity(Intent(this@StartActivity, RegisterActivity::class.java))
    }

    override fun openLogin() {
        startActivity(Intent(this@StartActivity, LoginActivity::class.java))
    }
}