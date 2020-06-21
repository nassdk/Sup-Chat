package com.nassdk.supchat.presentation.resetpassword.mvp

import com.arellomobile.mvp.InjectViewState
import com.nassdk.supchat.global.extensions.emailRegex
import com.nassdk.supchat.global.BasePresenter

@InjectViewState
class ResetPassPresenter : BasePresenter<ResetPassView>() {

    fun resetPassword(eMail: String) {

        if (!eMail.matches(emailRegex)) viewState.showEmailRegexError()
        else {
            auth.sendPasswordResetEmail(eMail).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewState.openLogin()
                    viewState.showSuccess()
                } else
                    viewState.showError(task.exception?.message)
            }
        }
    }
}