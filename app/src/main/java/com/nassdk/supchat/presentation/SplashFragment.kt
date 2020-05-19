package com.nassdk.supchat.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.arellomobile.mvp.MvpAppCompatFragment
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.global.BaseFragment
import com.nassdk.supchat.domain.global.navigation.Screens
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import java.util.concurrent.TimeUnit

class SplashFragment : BaseFragment() {

    private val router: Router by inject()

    override val resourceLayout = R.layout.screen_splash

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Observable.interval(3, TimeUnit.SECONDS)
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { openStartScreen() }
    }

    private fun openStartScreen() = router.newChain(Screens.StartScreen)

    override fun onBackPressed() { activity?.finish() }
}