package com.nassdk.supchat.presentation.lastchats.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.presentation.lastchats.adapter.ChatsAdapter
import com.nassdk.supchat.model.User
import com.nassdk.supchat.presentation.lastchats.mvp.ChatsPresenter
import com.nassdk.supchat.presentation.lastchats.mvp.ChatsView
import com.nassdk.supchat.presentation.searchusers.ui.SearchActivity
import com.github.clans.fab.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nassdk.supchat.presentation.chat.ui.ConversationActivity
import kotlin.collections.ArrayList

class ChatsFragment : MvpAppCompatFragment(), ChatsView {

    private lateinit var recView_displayChats: RecyclerView

    private lateinit var fabPen: FloatingActionButton

    private lateinit var fbUser: FirebaseUser
    private lateinit var reference: DatabaseReference


    private var spinner: ProgressBar? = null

    @InjectPresenter
    lateinit var presenter: ChatsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_chats, container, false)

        initViews()

        spinner = view.findViewById(R.id.spinner)
        spinner?.visibility = View.VISIBLE

        recView_displayChats = view.findViewById(R.id.recView_displayChats)
        recView_displayChats.setHasFixedSize(true)
        recView_displayChats.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

        fbUser = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Chats")

        fabPen = view.findViewById(R.id.fab_newPen)

        presenter.setUpChats()
        fabPen.setOnClickListener { presenter.toSearch() }
        return view
    }

    private fun initViews() {

    }

    override fun setAdapter(list: ArrayList<User>) {
        val chatsAdapter = ChatsAdapter(list) { id ->
            val intent = Intent(context, ConversationActivity::class.java)
            intent.putExtra("userId", id)
            startActivity(intent)
        }
        recView_displayChats.adapter = chatsAdapter
    }

    override fun openSearchUser() {
        val intent = Intent(context, SearchActivity::class.java)
        startActivity(intent)
    }

    override fun showProgress() {
        spinner?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        spinner?.visibility = View.GONE
    }
}