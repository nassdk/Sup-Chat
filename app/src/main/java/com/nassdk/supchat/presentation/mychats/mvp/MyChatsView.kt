package com.nassdk.supchat.presentation.mychats.mvp

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.domain.model.User
import com.nassdk.supchat.domain.global.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface MyChatsView : BaseView {

    fun openSearchUser()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setAdapter(list: ArrayList<User>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgress(show: Boolean)

}