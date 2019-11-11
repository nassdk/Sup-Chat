package com.nassdk.supchat.presentation.startscreen.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class StartPresenter : MvpPresenter<StartView>() {

    fun toRegisterActivity() = viewState.openRegister()
    fun toLoginActivity() = viewState.openLogin()
}