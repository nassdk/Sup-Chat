package com.example.firabasefirstexperience.kotlin.presentation.chat.mvp

import com.arellomobile.mvp.MvpView
import com.example.firabasefirstexperience.kotlin.model.Chat
import java.util.ArrayList

interface ChatActivityView : MvpView {

    fun showEmptyError()
    fun toDiffProfile()
    fun setAdapter(listOfChats: ArrayList<Chat>)
}