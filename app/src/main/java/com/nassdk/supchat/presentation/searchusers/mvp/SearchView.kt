package com.nassdk.supchat.presentation.searchusers.mvp

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.domain.model.User
import com.nassdk.supchat.global.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchView : BaseView {

    fun setData(usersList: ArrayList<User>)
}