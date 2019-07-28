package com.example.firabasefirstexperience.kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.java.model.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_header.*
import java.util.HashMap

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var drawerLayout: DrawerLayout? = null
    private var reference: DatabaseReference? = null
    private var user: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user!!.uid)

        setSupportActionBar(toolBarChats)
        toolBarChats?.inflateMenu(R.menu.menu_main)


        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolBarChats, R.string.nav_drawer_open, R.string.nav_drawer_close)
        toggle.isDrawerIndicatorEnabled
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        val header = nav_view.getHeaderView(0)

        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)

                if(user != null) {
                    navHeader_tvUserName.text = user.userName
                    navHeader_tvEmail.text = user.geteMail()

                    Glide
                            .with(this@MainActivity)
                            .load(user.imageURL)
                            .into(navHeader_ivProfile)
                }

            }

        })


    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        p0.isCheckable = false

        when (p0.itemId) {
            R.id.nav_Home -> {
                Log.e("dsa", "nav_home is chosen")
            }

            R.id.nav_Profile -> {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_Settings -> {

            }

            R.id.nav_LogOut -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@MainActivity, StartActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun status(status: String) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user!!.uid)
        val statusMap = HashMap<String, Any>()
        statusMap["status"] = status

        reference!!.updateChildren(statusMap)
    }


    override fun onPostResume() {
        super.onPostResume()
        status("online")
    }

    override fun onPause() {
        super.onPause()
        status("offline")
    }
}