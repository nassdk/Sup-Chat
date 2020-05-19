package com.nassdk.supchat.presentation.searchusers.provider

import com.example.domain.model.User
import com.nassdk.supchat.presentation.searchusers.mvp.SearchPresenter
import java.util.ArrayList

class SearchProvider(var presenter: SearchPresenter) {
    fun searchView(newText: String, usersList: List<User>): ArrayList<User> {
        val myList = ArrayList<User>()

        for (user in usersList) {
            if (user.userName?.toLowerCase()!!.contains(newText.toLowerCase())) {
                myList.add(user)
            }
        }
        return myList
    }

}