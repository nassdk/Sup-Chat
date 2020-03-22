package com.nassdk.supchat.presentation.registerscreen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.afterTextChanged
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.domain.extensions.toTextString
import com.nassdk.supchat.domain.extensions.toast
import com.nassdk.supchat.domain.global.BaseFragment
import com.nassdk.supchat.presentation.registerscreen.mvp.RegisterPresenter
import com.nassdk.supchat.presentation.registerscreen.mvp.RegisterView
import kotlinx.android.synthetic.main.screen_registration.*

class RegistrationFragment : BaseFragment(), RegisterView {

    override val resourceLayout = R.layout.screen_registration

    @InjectPresenter
    lateinit var registerPresenter: RegisterPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view = view)
    }

    private fun initViews(view: View) {

//        setSupportActionBar(tbForRegisterActivity)
//        supportActionBar?.title = "Sup Chat"
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etEmail   .afterTextChanged { validateFields() }
        etUserName.afterTextChanged { validateFields() }
        etPassword.afterTextChanged { validateFields() }

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

    private fun validateFields() {

        butSingUp.isEnabled =
                etEmail.   toTextString().isNotEmpty() &&
                etPassword.toTextString().isNotEmpty() &&
                etUserName.toTextString().isNotEmpty()

        if (etEmail.toTextString().isNotEmpty() && etPassword.toTextString().isNotEmpty() && etUserName.toTextString().isNotEmpty())
            butSingUp.background = ContextCompat.getDrawable(context!!, R.drawable.background_ripple_selector_base_pink)
        else
            butSingUp.background = ContextCompat.getDrawable(context!!, R.drawable.backgronud_gray_background_rounded_eighteen)
    }

    override fun showPassError()       = toast(getString(R.string.user_registration_password_error_message))
    override fun showRegisterError()   = toast(getString(R.string.user_registration_failure_message))
    override fun showSuccessMessage()  = toast(getString(R.string.user_registration_succes_message))
    override fun showEmailRegexError() = toast(getString(R.string.user_registration_email_error_message))

    override fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(context!!)
        builder
                .setTitle(getString(R.string.warning_message_title))
                .setMessage(getString(R.string.warning_no_internet_message))
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setNegativeButton(getString(R.string.warning_alert_exit_button_title)
                ) { _, _ -> activity?.finish() }
        val alert = builder.create()
        alert.show()
    }
}