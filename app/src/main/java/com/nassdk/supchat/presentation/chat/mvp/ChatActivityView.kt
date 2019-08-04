package com.nassdk.supchat.presentation.chat.mvp

import com.arellomobile.mvp.MvpView
import com.nassdk.supchat.model.Chat
import java.util.ArrayList

interface ChatActivityView : MvpView {

    fun showEmptyError()
    fun toDiffProfile()
    fun setAdapter(listOfChats: ArrayList<Chat>)
}