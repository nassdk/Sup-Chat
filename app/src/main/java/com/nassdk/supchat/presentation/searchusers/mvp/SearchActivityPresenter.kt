package com.nassdk.supchat.presentation.searchusers.mvp

import android.widget.SearchView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.nassdk.supchat.model.User
import com.nassdk.supchat.presentation.searchusers.SearchActivityProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.ArrayList

@InjectViewState
class SearchActivityPresenter : MvpPresenter<SearchActivityView>() {


    fun searchUser(searchView: SearchView, usersList: List<User>) {
        searchView.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        viewState.setAdapter(SearchActivityProvider(presenter = this@SearchActivityPresenter).searchView(newText, usersList))
                        return true
                    }
                })
    }


    fun displayUsers(usersList: List<User>) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val fbUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                (usersList as ArrayList<User>).clear()

                for (snapshot in p0.children) {
                    val user = snapshot.getValue(User::class.java)

                    if (user != null) {

                        if (user.id != fbUser.uid) {
                            usersList.add(user)
                        }
                    }
                }
                viewState.setAdapter(usersList)
            }
        })
    }
}