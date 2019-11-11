package com.nassdk.supchat.presentation.lastchats.mvp

import com.arellomobile.mvp.MvpView
import com.nassdk.supchat.domain.model.User

interface ChatsView : MvpView {

    fun setAdapter(list: ArrayList<User>)
    fun openSearchUser()
    fun showProgress(show: Boolean)
}