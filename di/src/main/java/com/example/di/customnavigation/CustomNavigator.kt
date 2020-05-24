package com.example.di.customnavigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command

class CustomNavigator : SupportAppNavigator {

    private var id: Int = 0
    private var fragmentMng: FragmentManager? = null
    private var onNext: (() -> Unit)? = null
    private var lastHideScreen: Fragment? = null
    var lastShowScreen: SupportAppScreen? = null


    constructor(
            fragmentActivity: FragmentActivity,
            containerId: Int
    ) : super(fragmentActivity, containerId) {
        this.id = containerId
    }

    constructor(
            fragmentActivity: FragmentActivity,
            fragmentManager: FragmentManager,
            containerId: Int,
            onNext: (() -> Unit)? = null
    ) : super(fragmentActivity, fragmentManager, containerId) {
        this.id = containerId
        this.fragmentMng = fragmentManager
        this.onNext = onNext
    }

    override fun activityBack() {
        onNext?.run { this() } ?: super.activityBack()
    }

    override fun applyCommand(command: Command) {
        when (command) {
            is ShowCommand -> showScreen(command)
            is HideCommand -> hideScreen(command)
            is AllowStateLoss -> allowStateLoss(command)
            else -> super.applyCommand(command)
        }
    }


    private fun allowStateLoss(allowStateLoss: AllowStateLoss) {
        if (allowStateLoss.screen is SupportAppScreen) {
            val fragment = createFragment(allowStateLoss.screen)
            val fragmentTransaction = fragmentManager?.beginTransaction()

            fragment?.let {
                fragmentTransaction
                        ?.replace(containerId, it)
                        ?.addToBackStack(allowStateLoss.screen.screenKey)
                        ?.commitAllowingStateLoss()
            }
        }
    }

    /**
     * отображает фрагмент, если он скрыт. Либо добавляет его в контейнер
     * */
    private fun showScreen(showCommand: ShowCommand) {
        if (showCommand.screen is SupportAppScreen) {
            lastShowScreen?.run {
                hideScreen(HideCommand(this))
            }
            val fragment = createFragment(showCommand.screen)

            if (fragment?.isHidden == true)
                fragmentManager
                    ?.beginTransaction()
                    ?.show(fragment)
                    ?.commitNow()
            else fragment?.let {
                fragmentManager?.beginTransaction()
                        ?.add(containerId, it)
                        ?.commitNow()
            }
            lastShowScreen = showCommand.screen
        }
    }

    private fun hideScreen(hideCommand: HideCommand) {
        if (hideCommand.screen is SupportAppScreen) {
            val fragment = createFragment(hideCommand.screen)

            if (fragment?.isHidden == false) {
                fragment.let {
                    fragmentManager
                            ?.beginTransaction()
                            ?.hide(it)
                            ?.commitNow()
                }
                lastHideScreen = fragment
            }
        }
    }
}