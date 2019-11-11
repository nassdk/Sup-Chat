package com.nassdk.supchat.presentation.chat.mvp

import com.arellomobile.mvp.MvpView
import com.nassdk.supchat.domain.model.Chat
import com.nassdk.supchat.domain.model.User
import java.util.ArrayList

interface ConversationView : MvpView {

    fun showEmptyError()
    fun toDiffProfile()
    fun setAdapter(listOfChats: ArrayList<Chat>)
    fun showNoInternetDialog()
    fun setData(user: User)
}