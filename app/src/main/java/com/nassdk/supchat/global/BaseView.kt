package com.nassdk.supchat.global

import com.arellomobile.mvp.MvpView

interface BaseView: MvpView {

    fun showLoading()
    fun hideLoading()
}