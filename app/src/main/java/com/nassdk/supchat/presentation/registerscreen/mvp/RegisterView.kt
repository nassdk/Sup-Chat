package com.nassdk.supchat.presentation.registerscreen.mvp

import com.arellomobile.mvp.MvpView

interface RegisterView : MvpView {
    fun showEmptyError()
    fun showPassError()
    fun showNoInternetDialog()
    fun showSuccessMessage()
    fun showRegisterError()
}