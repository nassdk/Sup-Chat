package com.example.firabasefirstexperience.kotlin.presentation.searchusers

import com.example.firabasefirstexperience.kotlin.model.User
import com.example.firabasefirstexperience.kotlin.presentation.searchusers.mvp.SearchActivityPresenter
import java.util.ArrayList

class SearchActivityProvider(var presenter: SearchActivityPresenter) {
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