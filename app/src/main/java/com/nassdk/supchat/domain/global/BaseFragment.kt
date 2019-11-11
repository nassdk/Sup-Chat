package com.nassdk.supchat.domain.global

import com.arellomobile.mvp.MvpAppCompatFragment

abstract class BaseFragment: MvpAppCompatFragment() {
    abstract fun onBackPressed()
}