package com.nassdk.supchat.presentation.startscreen.mvp

import com.arellomobile.mvp.MvpView

interface StartActivityView : MvpView {

    fun openRegister()
    fun openLogin()
}