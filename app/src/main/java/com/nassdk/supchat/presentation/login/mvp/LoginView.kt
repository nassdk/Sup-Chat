package com.nassdk.supchat.presentation.login.mvp

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.nassdk.supchat.domain.global.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface LoginView : BaseView {
    fun showSuccess()
    fun showAuthError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openResetPass()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openMain()
}