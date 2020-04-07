package com.nassdk.supchat.presentation.resetpassword.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.nassdk.supchat.domain.extensions.emailRegex

@InjectViewState
class ResetPassPresenter : MvpPresenter<ResetPassView>() {

    fun resetPassword(eMail: String) {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        if (!eMail.matches(emailRegex)) viewState.showEmailRegexError()
        else {
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