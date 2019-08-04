package com.example.firabasefirstexperience.kotlin.presentation.lastchats.mvp

import com.arellomobile.mvp.MvpView
import com.example.firabasefirstexperience.kotlin.model.User

interface ChatsFragmentView : MvpView {

    fun setAdapter(list: ArrayList<User>)
    fun openSearchUser()
    fun showProgress()
    fun hideProgress()
}