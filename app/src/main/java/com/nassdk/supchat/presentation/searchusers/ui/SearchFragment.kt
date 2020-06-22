package com.nassdk.supchat.presentation.searchusers.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.domain.model.User
import com.nassdk.supchat.R
import com.nassdk.supchat.global.BaseFragment
import com.nassdk.supchat.presentation.searchusers.mvp.SearchPresenter
import kotlinx.android.synthetic.main.screen_search.view.*

class SearchFragment : BaseFragment(), com.nassdk.supchat.presentation.searchusers.mvp.SearchView {

    override val resourceLayout = R.layout.screen_search

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    private val adapter: UserAdapter by lazy {
        UserAdapter(onUserClicked = { presenter.toProfileScreen(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view = view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomNavigation(show = true)
    }

    private fun initViews(view: View) {

//        setSupportActionBar(toolBarSearch)
//        supportActionBar?.title = ""
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showBottomNavigation(show = false)

        view.recView_SearchUsers.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_main, menu)

        val item: MenuItem = menu.findItem(R.id.item_search)
        val searchView: SearchView = item.actionView as SearchView

        searchView.isIconified = false
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        presenter.searchUser(searchQuery = newText)
                        return true
                    }
                })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun setData(usersList: ArrayList<User>) {
        adapter.setData(users = usersList)
    }

    override fun onBackPressed() = presenter.onBackPressed()
}