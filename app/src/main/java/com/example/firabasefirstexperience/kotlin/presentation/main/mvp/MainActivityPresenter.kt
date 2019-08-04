package com.example.firabasefirstexperience.kotlin.presentation.main.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.firabasefirstexperience.kotlin.presentation.main.MainProvider

@InjectViewState
class MainActivityPresenter : MvpPresenter<MainActivityView>() {

    fun toLogOut() {
        viewState.openStart()
    }

    fun toProfile() {
        viewState.openProfile()
    }

    fun updateStatus(status: String) {
        MainProvider(presenter = this).loadStatus(status)
    }

}