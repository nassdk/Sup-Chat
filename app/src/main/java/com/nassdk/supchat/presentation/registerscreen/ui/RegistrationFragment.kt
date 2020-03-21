package com.nassdk.supchat.presentation.registerscreen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.presentation.registerscreen.mvp.RegisterPresenter
import com.nassdk.supchat.presentation.registerscreen.mvp.RegisterView
import kotlinx.android.synthetic.main.screen_registration.*

class RegistrationFragment : MvpAppCompatFragment(), RegisterView {

    @InjectPresenter
    lateinit var registerPresenter: RegisterPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            layoutInflater.inflate(R.layout.screen_registration, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view = view)
    }

    private fun initViews(view: View) {

//        setSupportActionBar(tbForRegisterActivity)
//        supportActionBar?.title = "Sup Chat"
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        butSingUp.setOnClickListener {
            if (!isNetworkAvailable(context = context!!)) showNoInternetDialog()
            else {
                registerPresenter.registerUser(
                        etUserName.text.toString(),
                        etPassword.text.toString(),
                        etUserName.text.toString()
                )
            }
        }
    }

    override fun showEmptyError() = Toast.makeText(context, getString(R.string.user_registration_fields_empty_error_message), Toast.LENGTH_SHORT).show()
    override fun showPassError() = Toast.makeText(context, getString(R.string.user_registration_password_error_message), Toast.LENGTH_SHORT).show()
    override fun showRegisterError() = Toast.makeText(context, getString(R.string.user_registration_failure_message), Toast.LENGTH_LONG).show()
    override fun showSuccessMessage() = Toast.makeText(context, getString(R.string.user_registration_succes_message), Toast.LENGTH_LONG).show()

    override fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(getString(R.string.warning_message_title))
                .setMessage(getString(R.string.warning_no_internet_message))
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setNegativeButton(getString(R.string.warning_alert_exit_button_title)
                ) { _, _ -> activity?.finish() }
        val alert = builder.create()
        alert.show()
    }
}