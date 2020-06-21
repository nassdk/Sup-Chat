package com.nassdk.supchat.presentation.main.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.example.di.customnavigation.CustomNavigator
import com.example.di.customnavigation.CustomRouter
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nassdk.supchat.R
import com.nassdk.supchat.global.extensions.*
import com.nassdk.supchat.global.extensions.makeGone
import com.nassdk.supchat.global.extensions.makeVisible
import com.nassdk.supchat.global.BaseFragment
import com.nassdk.supchat.global.navigation.Screens
import kotlinx.android.synthetic.main.screen_main_flow.*
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppScreen


class MainFlowFragment : BaseFragment() {

    override val resourceLayout = R.layout.screen_main_flow

    private val router   : CustomRouter          by inject()
    private val navigatorHolder: NavigatorHolder by inject()

    private lateinit var navigator: CustomNavigator
    private lateinit var screensList: MutableList<SupportAppScreen>
    private lateinit var bottomNavigation: BottomNavigationView

    var lastTabPosition = 0
    var isTabBackStack = false

    private val tabTransitionsHistory = arrayListOf<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        navigator = CustomNavigator(activity!!, childFragmentManager, R.id.container)
        navigator.setLaunchScreen(Screens.NewsScreen)

        initScreenList()
        initBottomNavigation()
    }

    private fun initScreenList() {

        screensList = arrayListOf(
                Screens.NewsScreen,
                Screens.ChatsScreen,
                Screens.ProfileScreen
        )
    }

    private fun initBottomNavigation() {

        var screenPos = 0

        bottomNavigation = find(R.id.bottomNavigationView)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_news    -> screenPos = 0
                R.id.navigation_chats   -> screenPos = 1
                R.id.navigation_profile -> screenPos = 2
            }

            val screen = (screensList[screenPos].fragment) as BaseFragment

            if (navigator.lastShowScreen == screensList[screenPos]) {
                screen.resetStack()
                return@setOnNavigationItemSelectedListener true
            }


            if (!isTabBackStack) {
                addTabHistory(lastTabPosition)
            } else {
                isTabBackStack = false
            }

            lastTabPosition = screenPos

            router.showOrOpenScreen(screensList[screenPos])
            true
        }
    }

    private fun addTabHistory(tabPos: Int) {
        tabTransitionsHistory.add(tabPos)
        if (tabTransitionsHistory.size > 3)
            tabTransitionsHistory.removeAt(0)
    }

    override fun showBottomNavigation(show: Boolean) {

        if (show) bottomNavigationView.makeVisible() else bottomNavigationView.makeGone()

        val layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        )

        layoutParams.setMargins(
                0, 0, 0,
                if (show)
                    resources.getDimension(R.dimen.materialize_toolbar).toInt()
                else
                    0
        )

        container.layoutParams = layoutParams
    }

    override fun previousTab() {
        if (tabTransitionsHistory.isNotEmpty()) {
            isTabBackStack = true
            toSwitchTab(tabTransitionsHistory.last())
            tabTransitionsHistory.removeAt(tabTransitionsHistory.size - 1)
            Log.e("tabStack", "$tabTransitionsHistory")
        } else {
//            if (doubleBackToExitPressedOnce) {
//                activity?.finish()
//            }

            //doubleBackToExitPressedOnce = true
            //toast(R.string.clickDoubleForExit)

            //Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }

    override fun toSwitchTab(pos: Int) {
        val itemView = bottomNavigation.getChildAt(pos) as BottomNavigationItemView
        itemView.performClick()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() = (navigator.lastShowScreen?.fragment as BaseFragment).onBackPressed()
}