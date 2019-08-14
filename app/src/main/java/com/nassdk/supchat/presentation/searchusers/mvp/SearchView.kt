package com.nassdk.supchat.presentation.searchusers.mvp

import com.arellomobile.mvp.MvpView
import com.nassdk.supchat.model.User

interface SearchView : MvpView {

    fun setAdapter(usersList: ArrayList<User>)
}