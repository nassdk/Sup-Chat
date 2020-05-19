package com.nassdk.supchat.presentation.diffprofile.mvp

import com.arellomobile.mvp.MvpView
import com.example.domain.model.User

interface DiffProfileView : MvpView {

    fun openChat()
    fun showNoInternetDialog()
    fun showData(user: User)
}