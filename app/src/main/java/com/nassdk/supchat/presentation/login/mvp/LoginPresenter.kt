package com.nassdk.supchat.presentation.login.mvp

import com.arellomobile.mvp.InjectViewState
import com.nassdk.supchat.domain.global.BasePresenter
import com.nassdk.supchat.domain.global.navigation.Screens

@InjectViewState
class LoginPresenter : BasePresenter<LoginView>() {

    fun userResetPass() = viewState.openResetPass()

    fun userLog(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        viewState.openMain()
                        viewState.showSuccess()
                    } else viewState.showAuthError()
                }
    }

    fun openResetPass() = router.navigateTo(Screens.ResetPassScreen)
    fun openMain()      = router.newRootChain(Screens.MainScreen)
}