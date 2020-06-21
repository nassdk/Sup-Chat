package com.nassdk.supchat.presentation.registerscreen.mvp

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.nassdk.supchat.global.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface RegisterView : BaseView {
    fun showEmailRegexError()
    fun showPassError()
    fun showNoInternetDialog()
    fun showSuccessMessage()
    fun showRegisterError()
}