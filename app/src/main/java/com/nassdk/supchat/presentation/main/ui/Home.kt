package com.nassdk.supchat.presentation.main.ui

import android.os.Bundle
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.domain.model.User
import com.google.android.material.navigation.NavigationView
import com.nassdk.supchat.R
import com.nassdk.supchat.presentation.main.mvp.MainPresenter
import com.nassdk.supchat.presentation.main.mvp.MainView

class Home : MvpAppCompatActivity(), MainView, NavigationView.OnNavigationItemSelectedListener {

    //private lateinit var drawerLayout: DrawerLayout

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_main_flow)

        mainPresenter.fetchData()
        initViews()
    }

    private fun initViews() {

//        setSupportActionBar(toolBarChats)
//        toolBarChats?.inflateMenu(R.menu.menu_main)
//
//        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolBarChats, R.string.nav_drawer_open, R.string.nav_drawer_close)
//        toggle.isDrawerIndicatorEnabled
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        nav_view.setNavigationItemSelectedListener(this)
//        val header = nav_view.getHeaderView(0)
    }


    override fun sendStatus(status: String) = mainPresenter.updateStatus(status)

    override fun openStart() {
//        startActivity(Intent(this, StartActivity::class.java))
    }

    override fun openProfile() {}


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isCheckable = false

//        when (item.itemId) {
//            R.id.nav_Home -> {
//            }
//            R.id.nav_LogOut -> {
//                mainPresenter.toLogOut()
//            }
//            R.id.nav_Settings -> {
//            }
//            R.id.nav_Profile -> {
//                mainPresenter.toProfile()
//            }
//        }
//        drawerLayout.closeDrawer(GravityCompat.START)
        return false
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

//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            startActivity(Intent(this@Home, StartActivity::class.java))
//            finish()
//        }
    }

    override fun showData(user: User) {

//        navHeader_tvUserName.text = user.userName
//        navHeader_tvEmail.text = user.geteMail()
//
//        if (user.imageURL == "default") {
//            navHeader_ivProfile.setImageResource(R.mipmap.ic_launcher_round)
//        } else {
//            Glide
//                    .with(this@Home)
//                    .load(user.imageURL)
//                    .into(navHeader_ivProfile)
//        }
    }
}