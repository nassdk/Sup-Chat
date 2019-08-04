package com.example.firabasefirstexperience.kotlin.presentation.searchusers.mvp

import com.arellomobile.mvp.MvpView
import com.example.firabasefirstexperience.kotlin.model.User

interface SearchActivityView : MvpView {

    fun setAdapter(usersList: ArrayList<User>)
}