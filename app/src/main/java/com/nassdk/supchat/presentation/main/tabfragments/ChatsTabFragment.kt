package com.nassdk.supchat.presentation.main.tabfragments

import android.os.Bundle
import android.view.View
import com.example.di.customnavigation.CustomNavigator
import com.example.di.customnavigation.CustomRouter
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.setLaunchScreen
import com.nassdk.supchat.domain.global.BaseFragment
import com.nassdk.supchat.domain.global.navigation.Screens
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.NavigatorHolder

class ChatsTabFragment: BaseFragment() {

    override val resourceLayout = R.layout.screen_chats_tab

    private val router         : CustomRouter by inject()
    private val navigatorHolder: NavigatorHolder by inject()

    private lateinit var navigator: CustomNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigator = CustomNavigator(activity!!, childFragmentManager, R.id.chatsTabContainer)
        navigator.setLaunchScreen(Screens.ChatsScreen)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {}
}