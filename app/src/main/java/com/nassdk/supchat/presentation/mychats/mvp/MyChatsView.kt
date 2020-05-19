package com.nassdk.supchat.presentation.mychats.mvp

import com.arellomobile.mvp.MvpView
import com.example.domain.model.User
import com.nassdk.supchat.domain.global.BaseView

interface MyChatsView : BaseView {

    fun setAdapter(list: ArrayList<User>)
    fun openSearchUser()
    fun showProgress(show: Boolean)
}