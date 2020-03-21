package com.nassdk.supchat.presentation.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.presentation.login.mvp.LoginPresenter
import com.nassdk.supchat.presentation.login.mvp.LoginView
import com.nassdk.supchat.presentation.resetpassword.ui.ResetPassActivity
import kotlinx.android.synthetic.main.screen_login.*
import kotlinx.android.synthetic.main.screen_login.view.*

class LoginFragment : MvpAppCompatFragment(), LoginView, View.OnClickListener {

    private lateinit var navController: NavController

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            layoutInflater.inflate(R.layout.screen_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view = view)
    }

    private fun initViews(view: View) {

        navController = Navigation.findNavController(view)

        view.butLogIn       .setOnClickListener(this)
        view.tvResetPassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.butLogIn -> {
                if (!isNetworkAvailable(context = context!!))
                    showNoInternetDialog()
                else
                    presenter.userLog(etEmail.text.toString(), etPassword.text.toString())
            }

            R.id.tvResetPassword -> presenter.userResetPass()
        }
    }

    override fun showEmptyError() = Toast.makeText(context, getString(R.string.login_field_fill_error_message), Toast.LENGTH_SHORT).show()
    override fun showSuccess()    = Toast.makeText(context, getString(R.string.login_success_enter_to_account_message), Toast.LENGTH_SHORT).show()
    override fun showAuthError()  = Toast.makeText(context, getString(R.string.login_failure_message), Toast.LENGTH_LONG).show()
    override fun openResetPass()  = startActivity(Intent(context, ResetPassActivity::class.java))
    override fun openMain() {}

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

