package com.nassdk.supchat.presentation.lastchats.mvp

import com.arellomobile.mvp.MvpView
import com.nassdk.supchat.model.User

interface ChatsFragmentView : MvpView {

    fun setAdapter(list: ArrayList<User>)
    fun openSearchUser()
    fun showProgress()
    fun hideProgress()
}