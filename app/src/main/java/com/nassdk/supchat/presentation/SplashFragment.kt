package com.nassdk.supchat.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.arellomobile.mvp.MvpAppCompatFragment
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.global.BaseFragment

class SplashFragment : BaseFragment() {

    override val resourceLayout = R.layout.screen_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.startFragment)
    }
}