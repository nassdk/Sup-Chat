package com.example.firabasefirstexperience.kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.java.activity.LoginActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_main)


        val butRegister = findViewById<Button>(R.id.butRegister)
        val butLog = findViewById<Button>(R.id.butLog)

        butRegister.setOnClickListener {
            val intent = Intent(this@StartActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        butLog.setOnClickListener {
            val intent = Intent(this@StartActivity, com.example.firabasefirstexperience.kotlin.LoginActivity::class.java)
            startActivity(intent)
        }
    }
}