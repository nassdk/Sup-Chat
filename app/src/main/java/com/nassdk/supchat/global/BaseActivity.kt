package com.nassdk.supchat.global

import android.os.Bundle
import androidx.annotation.CallSuper
import com.arellomobile.mvp.MvpAppCompatActivity

abstract class BaseActivity: MvpAppCompatActivity() {

    abstract val resourceLayout: Int

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resourceLayout)

        handleLaunch()
    }

    abstract fun handleLaunch()
}