package com.nassdk.supchat.domain.global.navigation

import com.nassdk.supchat.presentation.SplashFragment
import com.nassdk.supchat.presentation.login.ui.LoginFragment
import com.nassdk.supchat.presentation.registerscreen.ui.RegistrationFragment
import com.nassdk.supchat.presentation.resetpassword.ui.ResetPassFragment
import com.nassdk.supchat.presentation.startscreen.StartFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object SplashScreen : SupportAppScreen() {
        override fun getFragment()
                = SplashFragment()
    }

    object StartScreen : SupportAppScreen() {
        override fun getFragment()
                = StartFragment()
    }

    object LoginScreen : SupportAppScreen() {
        override fun getFragment()
                = LoginFragment()
    }

    object RegistrationScreen : SupportAppScreen() {
        override fun getFragment()
                = RegistrationFragment()
    }

    object ResetPassScreen : SupportAppScreen() {
        override fun getFragment()
                = ResetPassFragment()
    }
}