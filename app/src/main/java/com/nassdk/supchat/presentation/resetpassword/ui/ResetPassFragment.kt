package com.nassdk.supchat.presentation.resetpassword.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.*
import com.nassdk.supchat.domain.global.BaseFragment
import com.nassdk.supchat.presentation.resetpassword.mvp.ResetPassPresenter
import com.nassdk.supchat.presentation.resetpassword.mvp.ResetPassView
import kotlinx.android.synthetic.main.screen_reset_pass.*
import kotlinx.android.synthetic.main.screen_reset_pass.view.*

class ResetPassFragment : BaseFragment(), ResetPassView {

    override val resourceLayout = R.layout.screen_reset_pass

    private lateinit var navController: NavController

    @InjectPresenter
    lateinit var presenter: ResetPassPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = Navigation.findNavController(view)

        initViews(view = view)
    }

    private fun initViews(view: View) {

        view.etEmailReset.afterTextChanged { butReset.accessible(it.isNotEmpty()) }

        view.butReset.setOnClickListener {
            if (!isNetworkAvailable(context = context!!)) showNoInternetDialog()
            else presenter.resetPassword(etEmailReset.toTextString())
        }
    }


    override fun showSuccess()             = toast(getString(R.string.reset_pass_success_message))
    override fun showEmailRegexError()     = toast(getString(R.string.reset_pass_email_regex_error_message))
    override fun showError(error: String?) = toast(error ?: getString(R.string.common_error_message))

    override fun openLogin() { navController.navigate(R.id.loginFragment) }
}