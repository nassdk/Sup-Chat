package com.nassdk.supchat.presentation.login.mvp

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.domain.global.BasePresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class LoginPresenter constructor(private val router: Router)
    : BasePresenter<LoginView>(router = router) {

    fun onEmptyError() = viewState.showEmptyError()

    fun userResetPass() = viewState.openResetPass()

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
}