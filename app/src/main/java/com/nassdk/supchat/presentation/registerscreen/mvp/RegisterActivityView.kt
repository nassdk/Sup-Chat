package com.nassdk.supchat.presentation.registerscreen.mvp

import com.arellomobile.mvp.MvpView

interface RegisterActivityView : MvpView {
    fun showEmptyError()
    fun showPassError()
}