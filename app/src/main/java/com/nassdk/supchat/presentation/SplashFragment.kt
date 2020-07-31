package com.nassdk.supchat.presentation

import android.os.Bundle
import android.view.View
import com.example.di.customnavigation.CustomRouter
import com.nassdk.supchat.R
import com.nassdk.supchat.global.BaseFragment
import com.nassdk.supchat.global.navigation.Screens
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class SplashFragment : BaseFragment() {

    override val resourceLayout = R.layout.screen_splash

    private val router: CustomRouter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        subscriptions += Observable.interval(3, TimeUnit.SECONDS)
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { openStartScreen() }
    }

    private fun openStartScreen() = router.newRootScreen(Screens.StartScreen)

    override fun onBackPressed() { activity?.finish() }
}