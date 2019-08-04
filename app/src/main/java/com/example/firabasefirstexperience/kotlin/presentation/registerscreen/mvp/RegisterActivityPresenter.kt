package com.example.firabasefirstexperience.kotlin.presentation.registerscreen.mvp

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.firabasefirstexperience.kotlin.presentation.registerscreen.RegisterProvider

@InjectViewState
class RegisterActivityPresenter : MvpPresenter<RegisterActivityView>() {

    fun onEmptyError() {
        viewState.showEmptyError()
    }

    fun onPassError() {
        viewState.showPassError()
    }

    fun register(textEmail: String, textPassword: String, textUserName: String, imageURL: String, status: String, context: Context) {
        RegisterProvider(presenter = this, context = context).registerUser(textEmail, textPassword, textUserName, imageURL, status)
    }
}