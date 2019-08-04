package com.nassdk.supchat.presentation.main.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.nassdk.supchat.R
import com.nassdk.supchat.presentation.main.mvp.MainActivityPresenter
import com.nassdk.supchat.presentation.main.mvp.MainActivityView
import com.nassdk.supchat.model.User
import com.nassdk.supchat.presentation.profilescreen.ui.ProfileActivity
import com.nassdk.supchat.presentation.startscreen.ui.StartActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_header.*

class MainActivity : MvpAppCompatActivity(), MainActivityView, NavigationView.OnNavigationItemSelectedListener {


    private lateinit var fbUser: FirebaseUser
    private lateinit var reference: DatabaseReference
    private lateinit var drawerLayout: DrawerLayout

    @InjectPresenter
    lateinit var mainPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        fbUser = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fbUser.uid)

        setSupportActionBar(toolBarChats)
        toolBarChats?.inflateMenu(R.menu.menu_main)


        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolBarChats, R.string.nav_drawer_open, R.string.nav_drawer_close)
        toggle.isDrawerIndicatorEnabled
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        val header = nav_view.getHeaderView(0)


        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)

                if (user != null) {
                    navHeader_tvUserName!!.text = user.userName
                    navHeader_tvEmail!!.text = user.geteMail()

                    if (user.imageURL == "default") {
                        navHeader_ivProfile.setImageResource(R.mipmap.ic_launcher_round)
                    } else {
                        Glide
                                .with(this@MainActivity)
                                .load(user.imageURL)
                                .into(navHeader_ivProfile)
                    }
                }

            }
        })
    }


    override fun sendStatus(status: String) {
        mainPresenter.updateStatus(status)
    }


    override fun openStart() {
        startActivity(Intent(applicationContext, StartActivity::class.java))
    }

    override fun openProfile() {
        startActivity(Intent(applicationContext, ProfileActivity::class.java))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isCheckable = false

        when (item.itemId) {
            R.id.nav_Home -> {
            }
            R.id.nav_LogOut -> {
                mainPresenter.toLogOut()
            }
            R.id.nav_Settings -> {
            }
            R.id.nav_Profile -> {
                mainPresenter.toProfile()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostResume() {
        super.onPostResume()
        sendStatus("online")
    }

    override fun onPause() {
        super.onPause()
        sendStatus("offline")
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            startActivity(Intent(this@MainActivity, StartActivity::class.java))
            finish()
        }
    }
}