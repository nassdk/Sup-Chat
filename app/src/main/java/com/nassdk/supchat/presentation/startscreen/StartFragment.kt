package com.nassdk.supchat.presentation.startscreen

import android.os.Bundle
import android.view.View
import com.example.di.customnavigation.CustomRouter
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.global.BaseFragment
import com.nassdk.supchat.domain.global.navigation.Screens
import kotlinx.android.synthetic.main.screen_start.view.*
import org.koin.android.ext.android.inject

class StartFragment : BaseFragment(), View.OnClickListener {

    override val resourceLayout = R.layout.screen_start

    private val router: CustomRouter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.butLog     .setOnClickListener(this)
        view.butRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.butLog      -> openLogin()
            R.id.butRegister -> openRegister()
        }
    }

    override fun onBackPressed() { activity?.finish() }

    private fun openRegister() = router.navigateTo(Screens.RegistrationScreen)
    private fun openLogin()    = router.navigateTo(Screens.LoginScreen)
}