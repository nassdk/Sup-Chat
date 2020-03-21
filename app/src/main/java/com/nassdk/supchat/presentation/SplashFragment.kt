package com.nassdk.supchat.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.arellomobile.mvp.MvpAppCompatFragment
import com.nassdk.supchat.R

class SplashFragment : MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            layoutInflater.inflate(R.layout.screen_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.startFragment)
    }
}