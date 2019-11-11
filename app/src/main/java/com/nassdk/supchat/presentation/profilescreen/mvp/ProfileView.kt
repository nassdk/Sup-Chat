package com.nassdk.supchat.presentation.profilescreen.mvp

import com.arellomobile.mvp.MvpView
import com.nassdk.supchat.domain.model.User
import java.lang.Exception

interface ProfileView : MvpView {

    fun showNoImageError()
    fun showFailError(error: Exception)
    fun showProgressError()
    fun showProgress(show: Boolean)
    fun uploadInProgress()
    fun showNoInternetDialog()
    fun showData(user: User)
    fun enablePhotoFab(enable: Boolean)
}