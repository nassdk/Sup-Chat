package com.nassdk.supchat.presentation.lastchats.mvp

import com.arellomobile.mvp.MvpView
import com.nassdk.supchat.model.User

interface ChatsView : MvpView {

    fun setAdapter(list: ArrayList<User>)
    fun openSearchUser()
    fun showProgress()
    fun hideProgress()
}