package com.nassdk.supchat.presentation.registerscreen.ui

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.accessible
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.domain.extensions.text
import com.nassdk.supchat.domain.extensions.toast
import com.nassdk.supchat.domain.global.BaseFragment
import com.nassdk.supchat.presentation.registerscreen.mvp.RegisterPresenter
import com.nassdk.supchat.presentation.registerscreen.mvp.RegisterView
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.screen_registration.*
import kotlinx.android.synthetic.main.screen_registration.view.*

class RegistrationFragment : BaseFragment(), RegisterView {

    override val resourceLayout = R.layout.screen_registration

    @InjectPresenter
    lateinit var presenter: RegisterPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view = view)
        setupToolbar(title = "Sign up", showNavIcon = true)
    }

    private fun initViews(view: View) {

        view.butSingUp.setOnClickListener {
            if (!isNetworkAvailable(context = context!!)) showNoInternetDialog()
            else {
                presenter.registerUser(
                        view.etUserName.text(),
                        view.etPassword.text(),
                        view.etUserName.text()
                )
            }
        }

        subscriptions += Observables.combineLatest(
                RxTextView.textChanges(etEmail),
                RxTextView.textChanges(etPassword),
                RxTextView.textChanges(etUserName)
        ) { email, password, name ->
            email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() }
                .subscribeBy { butSingUp.accessible(it) }
    }


    override fun onBackPressed()       = presenter.onBackPressed()

    override fun showPassError()       = toast(getString(R.string.user_registration_password_error_message))
    override fun showRegisterError()   = toast(getString(R.string.user_registration_failure_message))
    override fun showSuccessMessage()  = toast(getString(R.string.user_registration_succes_message))
    override fun showEmailRegexError() = toast(getString(R.string.user_registration_email_error_message))
}