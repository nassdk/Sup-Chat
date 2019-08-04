package com.example.firabasefirstexperience.kotlin.presentation.profilescreen.mvp

import com.arellomobile.mvp.MvpView
import java.lang.Exception

interface ProfileActivityView : MvpView {

    fun showNoImageError()
    fun showFailError(error: Exception)
    fun showProgressError()
    fun showProgress()
    fun hideProgress()
    fun uploadInProgress()

}