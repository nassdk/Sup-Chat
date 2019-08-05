package com.nassdk.supchat.presentation.diffprofile.mvp

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.nassdk.supchat.network.isNetworkAvailable

@InjectViewState
class DiffProfilePresenter : MvpPresenter<DiffProfileView>() {

    fun toChat() {
        viewState.openChat()
    }

    fun checkInternetConnection(context: Context) : Boolean {
        if(!isNetworkAvailable(context)) {
            viewState.showDialog()
            return true
        }
        return false
    }
}