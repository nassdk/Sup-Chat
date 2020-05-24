package com.example.di.module

import com.example.di.customnavigation.CustomRouter
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

val navigationModule = module {

    val cicerone: Cicerone<CustomRouter> = Cicerone.create(CustomRouter())
    single { cicerone.router }
    single { cicerone.navigatorHolder }
}