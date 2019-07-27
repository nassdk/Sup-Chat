package com.example.firabasefirstexperience.kotlin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.firabasefirstexperience.R
import com.google.firebase.auth.FirebaseAuth

class StartLoadingActivity : AppCompatActivity() {

    private val LOADING_SCREEN_SHOW_TIME = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_loading)

        val user = FirebaseAuth.getInstance().currentUser

        Handler().postDelayed({
            if (user != null) {
                val intent = Intent(this@StartLoadingActivity, com.example.firabasefirstexperience.kotlin.MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@StartLoadingActivity, StartActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, LOADING_SCREEN_SHOW_TIME.toLong())
    }
}