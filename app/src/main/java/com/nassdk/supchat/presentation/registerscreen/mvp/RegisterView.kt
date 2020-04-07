package com.nassdk.supchat.presentation.registerscreen.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RegisterView : MvpView {
    fun showEmailRegexError()
    fun showPassError()
    fun showNoInternetDialog()
    fun showSuccessMessage()
    fun showRegisterError()
}