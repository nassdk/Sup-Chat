package com.example.firabasefirstexperience.kotlin.presentation.main.mvp

import com.arellomobile.mvp.MvpView


interface MainActivityView : MvpView {

    fun sendStatus(status: String)
    fun openStart()
    fun openProfile()
}