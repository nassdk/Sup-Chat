package com.nassdk.supchat.presentation.login.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth

@InjectViewState
class LoginActivityPresenter : MvpPresenter<LoginActivityView>() {

    fun onEmptyError() {
        viewState.showEmptyError()
    }

    fun userLog(email: String, password: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        viewState.openMain()
                        viewState.showSuccess()
                    } else {
                        viewState.showAuthError()
                    }

                }
    }

    fun userResetPass() {
        viewState.openResetPass()
    }
}