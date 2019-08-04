package com.example.firabasefirstexperience.kotlin.presentation.resetpassword.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth

@InjectViewState
class ResetPassActivityPresenter : MvpPresenter<ResetPassActivityView>() {

    fun resetPassword(eMail: String) {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        if (eMail.isEmpty()) {
            viewState.showEmptyError()
        } else run {
            auth.sendPasswordResetEmail(eMail).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewState.openLogin()
                    viewState.showSuccess()
                } else {
                    val error = task.exception!!.message
                    viewState.showError(error)
                }
            }
        }
    }
}