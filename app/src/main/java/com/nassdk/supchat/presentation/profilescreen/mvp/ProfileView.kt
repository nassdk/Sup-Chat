package com.nassdk.supchat.presentation.profilescreen.mvp

import com.arellomobile.mvp.MvpView
import com.example.domain.model.User
import com.nassdk.supchat.domain.global.BaseView
import java.lang.Exception

interface ProfileView : BaseView {

    fun showNoImageError()
    fun showFailError(error: Exception)
    fun showProgressError()
    fun showProgress(show: Boolean)
    fun uploadInProgress()
    fun showNoInternetDialog()
    fun showData(user: User)
    fun enablePhotoFab(enable: Boolean)
}