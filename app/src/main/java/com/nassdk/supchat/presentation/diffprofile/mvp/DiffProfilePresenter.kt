package com.nassdk.supchat.presentation.diffprofile.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class DiffProfilePresenter : MvpPresenter<DiffProfileView>() {

    fun toChat() {
        viewState.openChat()
    }
}