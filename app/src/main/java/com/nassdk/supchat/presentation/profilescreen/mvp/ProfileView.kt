package com.nassdk.supchat.presentation.profilescreen.mvp

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.domain.model.User
import com.nassdk.supchat.domain.global.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface ProfileView : BaseView {

    fun showNoImageError()
    fun showFailError(error: Exception)
    fun showProgressError()
    fun uploadInProgress()
    fun showNoInternetDialog()
    fun showData(user: User)
    fun enablePhotoFab(enable: Boolean)
}