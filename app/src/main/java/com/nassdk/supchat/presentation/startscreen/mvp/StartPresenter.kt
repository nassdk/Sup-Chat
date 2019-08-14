package com.nassdk.supchat.presentation.startscreen.mvp

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.nassdk.supchat.network.isNetworkAvailable

@InjectViewState
class StartPresenter : MvpPresenter<StartView>() {

    fun toRegisterActivity() {
        viewState.openRegister()
    }

    fun toLoginActivity() {
        viewState.openLogin()
    }

    fun checkInternetConnection(context: Context) : Boolean {
        if(!isNetworkAvailable(context)) {
            viewState.showDialog()
            return true
        }
        return false
    }
}