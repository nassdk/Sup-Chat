package com.example.di.customnavigation

import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

class CustomRouter : Router() {

    var exitListener: (() -> Unit)? = null

    fun signInReplaceScreen(screen: Screen) = executeCommands(SignInCommand(screen))
    fun showOrOpenScreen(screen: Screen) = executeCommands(ShowCommand(screen))
    fun hideScreen(screen: Screen) = executeCommands(HideCommand(screen))
    fun allowStateLoss(screen: Screen) = executeCommands(AllowStateLoss(screen))


    override fun exit() {
        exitListener?.invoke()
        super.exit()
    }
}