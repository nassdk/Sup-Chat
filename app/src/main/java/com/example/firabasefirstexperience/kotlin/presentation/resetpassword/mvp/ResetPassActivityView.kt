package com.example.firabasefirstexperience.kotlin.presentation.resetpassword.mvp

import com.arellomobile.mvp.MvpView


interface ResetPassActivityView : MvpView {
    fun showSuccess()
    fun showEmptyError()
    fun openLogin()
    fun showError(error: String?)
}