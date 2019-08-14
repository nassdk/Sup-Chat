package com.nassdk.supchat.presentation.login.mvp

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.nassdk.supchat.network.isNetworkAvailable

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {

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

    fun checkInternetConnection(context: Context) : Boolean {
        if(!isNetworkAvailable(context)) {
            viewState.showDialog()
            return true
        }
        return false
    }
}