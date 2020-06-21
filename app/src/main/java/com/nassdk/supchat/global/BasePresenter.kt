package com.nassdk.supchat.global

import com.arellomobile.mvp.MvpPresenter
import com.example.di.customnavigation.CustomRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BasePresenter<V : BaseView> : MvpPresenter<V>(), KoinComponent  {

    var subscriptions = CompositeDisposable()

    val auth    : FirebaseAuth     by lazy { FirebaseAuth.getInstance() }
    val database: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    val referenceUsers: DatabaseReference by lazy { database.getReference(USERS) }
    val referenceChats: DatabaseReference by lazy { database.getReference(CHATS) }

    val fbUser = FirebaseAuth.getInstance().currentUser

    val router: CustomRouter by inject()

    fun Disposable.untilDestroy() {
        subscriptions.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }

    open fun onBackPressed() = router.exit()

    companion object {
        const val USERS = "Users"
        const val CHATS = "Chats"
    }
}