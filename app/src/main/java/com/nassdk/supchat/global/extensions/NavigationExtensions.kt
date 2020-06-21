package com.nassdk.supchat.global.extensions

import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

fun Navigator.setLaunchScreen(screen: SupportAppScreen) {
    applyCommands(
            arrayOf(
                    BackTo(null),
                    Replace(screen)
            )
    )
}