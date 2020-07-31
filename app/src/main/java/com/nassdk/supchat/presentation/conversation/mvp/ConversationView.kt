package com.nassdk.supchat.presentation.conversation.mvp

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.domain.model.Chat
import com.example.domain.model.User
import com.nassdk.supchat.global.BaseView
import java.util.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface ConversationView : BaseView {

    fun showEmptyError()
    fun setupMessages(listOfChats: ArrayList<Chat>)
    fun setData(user: User)
}