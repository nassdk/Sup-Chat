package com.nassdk.supchat.presentation.login.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.accessible
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.domain.extensions.toTextString
import com.nassdk.supchat.domain.extensions.toast
import com.nassdk.supchat.domain.global.BaseFragment
import com.nassdk.supchat.presentation.login.mvp.LoginPresenter
import com.nassdk.supchat.presentation.login.mvp.LoginView
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
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
                    presenter.userLog(etEmail.toTextString(), etPassword.toTextString())
            }

            R.id.tvResetPassword -> presenter.userResetPass()
        }
    }

    override fun showSuccess()   = toast(getString(R.string.login_success_enter_to_account_message))
    override fun showAuthError() = toast(getString(R.string.login_failure_message))
    override fun openResetPass() = navController.navigate(R.id.resetPassFragment)
    override fun openMain() {}
}

