package com.nassdk.supchat.presentation.login.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface LoginView : MvpView {
    fun showSuccess()
    fun showAuthError()
    fun openResetPass()
    fun openMain()
}