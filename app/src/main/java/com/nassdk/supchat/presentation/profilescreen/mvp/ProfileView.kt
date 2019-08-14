package com.nassdk.supchat.presentation.profilescreen.mvp

import com.arellomobile.mvp.MvpView
import java.lang.Exception

interface ProfileView : MvpView {

    fun showNoImageError()
    fun showFailError(error: Exception)
    fun showProgressError()
    fun showProgress()
    fun hideProgress()
    fun uploadInProgress()
    fun showDialog()
}