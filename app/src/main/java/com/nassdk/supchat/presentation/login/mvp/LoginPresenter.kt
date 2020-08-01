package com.nassdk.supchat.presentation.login.mvp

import com.arellomobile.mvp.InjectViewState
import com.nassdk.supchat.global.BasePresenter
import com.nassdk.supchat.global.navigation.Screens

@InjectViewState
class LoginPresenter : BasePresenter<LoginView>() {

    fun userResetPass() = openResetPass()

    fun userLog(email: String, password: String) {

        viewState.showLocalLoading()

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        openMain()
                        viewState.showSuccess()
                    } else viewState.showAuthError()

                    viewState.hideLocalLoading()
                }
    }

    private fun openResetPass() = router.navigateTo(Screens.ResetPassScreen)
    private fun openMain()      = router.newRootChain(Screens.MainScreen)
}