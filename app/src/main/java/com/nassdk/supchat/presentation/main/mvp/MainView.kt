package com.nassdk.supchat.presentation.main.mvp

import com.arellomobile.mvp.MvpView


interface MainView : MvpView {

    fun sendStatus(status: String)
    fun openStart()
    fun openProfile()
}