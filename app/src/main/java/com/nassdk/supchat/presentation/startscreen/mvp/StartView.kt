package com.nassdk.supchat.presentation.startscreen.mvp

import com.arellomobile.mvp.MvpView

interface StartView : MvpView {

    fun openRegister()
    fun openLogin()
    fun showNoInternetDialog()
}