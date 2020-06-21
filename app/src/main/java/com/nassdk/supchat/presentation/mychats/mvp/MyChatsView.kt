package com.nassdk.supchat.presentation.mychats.mvp

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.domain.model.User
import com.nassdk.supchat.global.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface MyChatsView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setAdapter(list: ArrayList<User>)
}