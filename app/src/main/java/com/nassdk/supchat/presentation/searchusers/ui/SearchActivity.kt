package com.nassdk.supchat.presentation.searchusers.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.example.domain.model.User
import com.nassdk.supchat.presentation.searchusers.adapter.UserAdapter
import com.nassdk.supchat.presentation.searchusers.mvp.SearchPresenter
import com.nassdk.supchat.presentation.diffprofile.ui.DiffProfileActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlin.collections.ArrayList

class SearchActivity : MvpAppCompatActivity(), com.nassdk.supchat.presentation.searchusers.mvp.SearchView {

    private lateinit var usersList: List<User>

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    override fun onStart() {
        super.onStart()

        usersList = ArrayList()
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
        val userAdapter = UserAdapter(usersList) { id ->
            val intent = Intent(this, DiffProfileActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
        recView_SearchUsers.adapter = userAdapter
    }
}