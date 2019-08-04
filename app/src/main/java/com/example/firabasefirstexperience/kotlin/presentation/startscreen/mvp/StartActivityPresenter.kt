package com.example.firabasefirstexperience.kotlin.presentation.startscreen.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class StartActivityPresenter : MvpPresenter<StartActivityView>() {

    fun toRegisterActivity() {
        viewState.openRegister()
    }

    fun toLoginActivity() {
        viewState.openLogin()
    }
}