package com.nassdk.supchat.presentation.resetpassword.mvp

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.nassdk.supchat.global.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface ResetPassView : BaseView {
    fun showSuccess()
    fun showEmailRegexError()
    fun openLogin()
    fun showError(error: String?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLocalLoading()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideLocalLoading()
}