package com.nassdk.supchat.presentation.conversation.mvp

import com.example.domain.model.Chat
import com.example.domain.model.User
import com.nassdk.supchat.domain.global.BaseView
import java.util.ArrayList

interface ConversationView : BaseView {

    fun showEmptyError()
    fun setupMessages(listOfChats: ArrayList<Chat>)
    fun setData(user: User)
}