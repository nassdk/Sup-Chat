package com.nassdk.supchat.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.arellomobile.mvp.MvpAppCompatActivity
import com.nassdk.supchat.presentation.startscreen.ui.StartActivity


class StartLoadingActivity : MvpAppCompatActivity() {

    private val LOADING_SCREEN_SHOW_TIME = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.nassdk.supchat.R.layout.activity_start_loading)

        //val fireBaseUser = FirebaseAuth.getInstance().currentUser
        Handler().postDelayed({
            //if (fireBaseUser != null) {
//                val intent = Intent(this@StartLoadingActivity, MainActivity::class.java)
//                startActivity(intent)
//                finish()
            //} else {
            val intent = Intent(this@StartLoadingActivity, StartActivity::class.java)
            startActivity(intent)
            finish()
            //}
        }, LOADING_SCREEN_SHOW_TIME.toLong())
    }
}