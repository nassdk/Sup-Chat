package com.example.firabasefirstexperience.kotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.java.adapter.UserAdapter
import com.example.firabasefirstexperience.java.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_search.*
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {

    private var fbUser: FirebaseUser? = null
    private var usersList: List<User>? = null


    override fun onStart() {
        super.onStart()

        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        fbUser = FirebaseAuth.getInstance().currentUser

        usersList = ArrayList<User>()

        if(reference != null) {
            reference.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    (usersList as ArrayList<User>).clear()

                    for (snapshot in p0.children) {
                        val user = snapshot.getValue(User::class.java)

                        if (user != null && fbUser != null) {

                            if (user.id != fbUser!!.uid) {
                                (usersList as ArrayList<User>).add(user)
                            }
                        }
                    }

                    val userAdapter = UserAdapter(this@SearchActivity, usersList)
                    recView_SearchUsers.adapter = userAdapter

                }

            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

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

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                searchView(newText)
                return true
            }
        })


        return super.onCreateOptionsMenu(menu)
    }

    private fun searchView(newText: String) {
        val myList = ArrayList<User>()

        for (user in usersList!!) {
            if (user.userName.toLowerCase().contains(newText.toLowerCase())) {
                myList.add(user)
            }
        }

        val userAdapter = UserAdapter(this@SearchActivity,myList)
        recView_SearchUsers.adapter = userAdapter
    }
}

