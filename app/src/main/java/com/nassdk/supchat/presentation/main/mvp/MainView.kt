package com.nassdk.supchat.presentation.main.mvp

import com.arellomobile.mvp.MvpView
import com.example.domain.model.User


interface MainView : MvpView {

    fun sendStatus(status: String)
    fun openStart()
    fun openProfile()
    fun showData(user: User)
}