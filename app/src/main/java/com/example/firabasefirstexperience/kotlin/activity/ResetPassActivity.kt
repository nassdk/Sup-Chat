package com.example.firabasefirstexperience.kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firabasefirstexperience.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_pass.*
import kotlinx.android.synthetic.main.bar_layout.*

class ResetPassActivity : AppCompatActivity() {


    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)


        setSupportActionBar(tbForRegisterActivity)
        supportActionBar?.title = "Reset Password"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()

        butReset.setOnClickListener {
            val eMail = etEmailReset.text.toString()

            if (eMail.isEmpty()) {
                Toast.makeText(applicationContext, "Please, write your Email", Toast.LENGTH_SHORT).show()
            } else run {
                auth?.sendPasswordResetEmail(eMail)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@ResetPassActivity, "Message with instruction send to your Email. Please Check it", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@ResetPassActivity, LoginActivity::class.java))
                    } else {
                        val error = task.exception!!.message
                        Toast.makeText(this@ResetPassActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }
}