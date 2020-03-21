package com.nassdk.supchat.presentation.startscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nassdk.supchat.R
import kotlinx.android.synthetic.main.screen_start.view.*

class StartFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            layoutInflater.inflate(R.layout.screen_start, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = Navigation.findNavController(view)
        view.butLog.setOnClickListener(this)
        view.butRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.butLog -> openLogin()
            R.id.butRegister -> openRegister()
        }
    }

    private fun openRegister() = navController.navigate(R.id.registrationFragment)
    private fun openLogin() = navController.navigate(R.id.loginFragment)
}