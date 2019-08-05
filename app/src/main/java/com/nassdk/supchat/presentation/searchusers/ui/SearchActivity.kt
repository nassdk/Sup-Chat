package com.nassdk.supchat.presentation.searchusers.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.model.User
import com.nassdk.supchat.presentation.searchusers.UserAdapter
import com.nassdk.supchat.presentation.searchusers.mvp.SearchActivityPresenter
import com.nassdk.supchat.presentation.searchusers.mvp.SearchActivityView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlin.collections.ArrayList

class SearchActivity : MvpAppCompatActivity(), SearchActivityView {

    private lateinit var fbUser: FirebaseUser
    private lateinit var usersList: List<User>
    private lateinit var reference: DatabaseReference

    @InjectPresenter
    lateinit var presenter: SearchActivityPresenter

    override fun onStart() {
        super.onStart()

        reference = FirebaseDatabase.getInstance().getReference("Users")
        fbUser = FirebaseAuth.getInstance().currentUser!!

        usersList = ArrayList<User>()

        presenter.displayUsers(usersList)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolBarSearch)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recView_SearchUsers.layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
        recView_SearchUsers.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val item: MenuItem = menu!!.findItem(R.id.item_search)
        val searchView: SearchView = item.actionView as SearchView
        searchView.isIconified = false
        searchView.queryHint = "Search"

        presenter.searchUser(searchView, usersList)

        return super.onCreateOptionsMenu(menu)
    }

    override fun setAdapter(usersList: ArrayList<User>) {
        val userAdapter = UserAdapter(usersList)
        recView_SearchUsers.adapter = userAdapter
    }
}