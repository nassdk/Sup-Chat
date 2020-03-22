package com.nassdk.supchat.presentation.login.mvp

import com.arellomobile.mvp.MvpView

interface LoginView : MvpView {
    fun showSuccess()
    fun showAuthError()
    fun openResetPass()
    fun openMain()
    fun showNoInternetDialog()
}