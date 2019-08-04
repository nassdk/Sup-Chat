package com.nassdk.supchat.presentation.login.mvp

import com.arellomobile.mvp.MvpView

interface LoginActivityView : MvpView {
    fun showEmptyError()
    fun showSuccess()
    fun showAuthError()
    fun openResetPass()
    fun openMain()
}