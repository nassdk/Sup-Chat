package com.nassdk.supchat.presentation.login.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.afterTextChanged
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.domain.extensions.toTextString
import com.nassdk.supchat.domain.extensions.toast
import com.nassdk.supchat.domain.global.BaseFragment
import com.nassdk.supchat.presentation.login.mvp.LoginPresenter
import com.nassdk.supchat.presentation.login.mvp.LoginView
import kotlinx.android.synthetic.main.screen_login.*
import kotlinx.android.synthetic.main.screen_login.view.*

class LoginFragment : BaseFragment(), LoginView, View.OnClickListener {

    override val resourceLayout = R.layout.screen_login

    private lateinit var navController: NavController

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view = view)
    }

    private fun initViews(view: View) {

        navController = Navigation.findNavController(view)

        view.butLogIn       .setOnClickListener(this)
        view.tvResetPassword.setOnClickListener(this)

        //view.etEmail   .afterTextChanged { validateFields() }
        //view.etPassword.afterTextChanged { validateFields() }
    }

    private fun validateFields() {

        butLogIn.isEnabled = etEmail.toTextString().isNotEmpty() && etPassword.toTextString().isNotEmpty()

        if (etEmail.toTextString().isNotEmpty() && etPassword.toTextString().isNotEmpty())
            butLogIn.background = ContextCompat.getDrawable(context!!, R.drawable.background_ripple_selector_base_pink)
        else
            butLogIn.background = ContextCompat.getDrawable(context!!, R.drawable.backgronud_gray_background_rounded_eighteen)
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

    override fun showSuccess()   = toast(getString(R.string.login_success_enter_to_account_message))
    override fun showAuthError() = toast(getString(R.string.login_failure_message))
    override fun openResetPass() = navController.navigate(R.id.resetPassFragment)
    override fun openMain() {}
}

