package com.nassdk.supchat.presentation.resetpassword.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ResetPassView : MvpView {
    fun showSuccess()
    fun showEmailRegexError()
    fun openLogin()
    fun showError(error: String?)
}