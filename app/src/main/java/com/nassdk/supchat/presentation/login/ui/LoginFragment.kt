package com.nassdk.supchat.presentation.login.ui

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.nassdk.supchat.R
import com.nassdk.supchat.global.extensions.accessible
import com.nassdk.supchat.global.extensions.isNetworkAvailable
import com.nassdk.supchat.global.extensions.text
import com.nassdk.supchat.global.extensions.toast
import com.nassdk.supchat.global.BaseFragment
import com.nassdk.supchat.presentation.login.mvp.LoginPresenter
import com.nassdk.supchat.presentation.login.mvp.LoginView
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.screen_login.*
import kotlinx.android.synthetic.main.screen_login.view.*

class LoginFragment : BaseFragment(), LoginView, View.OnClickListener {

    override val resourceLayout = R.layout.screen_login

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view = view)
        setupToolbar(title = "Log in", showNavIcon = true)
    }

    private fun initViews(view: View) {

        view.butLogIn       .setOnClickListener(this)
        view.tvResetPassword.setOnClickListener(this)

        subscriptions += Observables.combineLatest(
                RxTextView.textChanges(etEmail),
                RxTextView.textChanges(etPassword)
        ) { email, password ->
            email.isNotEmpty() && password.isNotEmpty() }
                .subscribeBy { view.butLogIn.accessible(it) }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.butLogIn -> {
                if (!isNetworkAvailable(context = context!!))
                    showNoInternetDialog()
                else
                    presenter.userLog(etEmail.text(), etPassword.text())
            }

            R.id.tvResetPassword -> presenter.userResetPass()
        }
    }

    override fun openMain()       = presenter.openMain()
    override fun openResetPass()  = presenter.openResetPass()
    override fun onBackPressed()  = presenter.onBackPressed()
    override fun showSuccess()    = toast(getString(R.string.login_success_enter_to_account_message))
    override fun showAuthError()  = toast(getString(R.string.login_failure_message))
}

