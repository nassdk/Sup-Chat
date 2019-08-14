package com.nassdk.supchat.presentation.main.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.nassdk.supchat.presentation.main.provider.MainProvider

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

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