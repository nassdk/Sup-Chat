package com.nassdk.supchat.presentation.login.mvp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {

    fun userResetPass() = viewState.openResetPass()

    fun userLog(email: String, password: String) {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        when {
            email.isEmpty()    -> viewState.showEmptyError()
            password.isEmpty() -> viewState.showEmptyError()
            else               -> auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {
                                viewState.openMain()
                                viewState.showSuccess()
                            } else viewState.showAuthError()
                        }
        }
    }
}