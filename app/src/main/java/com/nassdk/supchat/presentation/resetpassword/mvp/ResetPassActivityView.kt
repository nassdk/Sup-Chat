package com.nassdk.supchat.presentation.resetpassword.mvp

import com.arellomobile.mvp.MvpView


interface ResetPassActivityView : MvpView {
    fun showSuccess()
    fun showEmptyError()
    fun openLogin()
    fun showError(error: String?)
    fun showDialog()
}