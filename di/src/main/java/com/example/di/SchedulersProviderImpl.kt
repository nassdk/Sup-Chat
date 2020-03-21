package com.example.di

import com.example.domain.common.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulersProviderImpl : SchedulersProvider {

    override val ui: Scheduler
        get() = AndroidSchedulers.mainThread()
    override val computation: Scheduler
        get() = Schedulers.computation()
    override val trampoline: Scheduler
        get() = Schedulers.trampoline()
    override val newThread: Scheduler
        get() = Schedulers.newThread()
    override val io: Scheduler
        get() = Schedulers.io()

}