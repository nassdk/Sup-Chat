package com.example.firabasefirstexperience.kotlin.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firabasefirstexperience.kotlin.model.Chat
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.kotlin.activity.SearchActivity
import com.example.firabasefirstexperience.kotlin.model.User
import com.example.firabasefirstexperience.kotlin.adapter.ChatsAdapterkt
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.ArrayList

class ChatsFragment : Fragment() {

    private var recView_displayChats: RecyclerView? = null

    private var fbUser: FirebaseUser? = null
    private var reference: DatabaseReference? = null

    private var usersList: List<String>? = null
    private var mList: List<User>? = null

    private var spinner: ProgressBar? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_chats, container, false)

        spinner = view.findViewById(R.id.spinner)
        spinner?.visibility = View.VISIBLE

        recView_displayChats = view.findViewById(R.id.recView_displayChats)
        recView_displayChats?.setHasFixedSize(true)
        recView_displayChats?.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)




        fbUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Chats")

        usersList = ArrayList()

        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                (usersList as ArrayList<String>).clear()

                for (snapshot in p0.children) {

                    val chat = snapshot.getValue(Chat::class.java)
                    if (chat != null) {

                        if (chat.sender == fbUser?.uid && (!(usersList as ArrayList<String>).contains(chat.receiver))) {
                            (usersList as ArrayList<String>).add(chat.receiver!!)
                        }

                        if (chat.receiver == fbUser?.uid && (!(usersList as ArrayList<String>).contains(chat.sender))) {
                            (usersList as ArrayList<String>).add(chat.sender!!)
                        }
                    }

                }

                readChats()
                spinner?.visibility = View.GONE

            }

        })

        val fab_newPen: FloatingActionButton = view.findViewById(R.id.fab_newPen)

        fab_newPen.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun readChats() {
        mList = ArrayList()

        reference = FirebaseDatabase.getInstance().getReference("Users")
        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                (mList as ArrayList<User>).clear()

                for (snapshot in p0.children) {
                    val user = snapshot.getValue(User::class.java)

                    for (id in (usersList as ArrayList<String>)) {
                        if (user?.id == id) {
                            if (mList?.size != 0) {
                                for (user1 in mList as ArrayList<User>) {
                                    if (user.id != user1.id) {
                                        (mList as ArrayList<User>).add(user)
                                        break
                                    }
                                }
                            } else {
                                (mList as ArrayList<User>).add(user)
                            }
                        }
                    }
                }

                val chatsAdapter = ChatsAdapterkt(mList as ArrayList<User>)
                recView_displayChats?.adapter = chatsAdapter
            }

        })
    }
}