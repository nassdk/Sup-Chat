package com.nassdk.supchat.presentation.lastchats.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.presentation.lastchats.adapter.ChatsAdapter
import com.nassdk.supchat.domain.model.User
import com.nassdk.supchat.presentation.lastchats.mvp.ChatsPresenter
import com.nassdk.supchat.presentation.lastchats.mvp.ChatsView
import com.nassdk.supchat.presentation.searchusers.ui.SearchActivity
import com.nassdk.supchat.presentation.chat.ui.ConversationActivity
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlin.collections.ArrayList

class ChatsFragment : MvpAppCompatFragment(), ChatsView {

    @InjectPresenter
    lateinit var presenter: ChatsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_chats, container, false)

        presenter.setUpChats()
        initViews(view = view)
        return view
    }

    private fun initViews(view: View) {

        spinner.visibility = View.VISIBLE
        recViewChats.setHasFixedSize(true)
        recViewChats.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        fabStartNewChat.setOnClickListener { presenter.toSearch() }
    }

    override fun setAdapter(list: ArrayList<User>) {
        val chatsAdapter = ChatsAdapter(list) { id ->
            val intent = Intent(context, ConversationActivity::class.java)
            intent.putExtra("userId", id)
            startActivity(intent)
        }
        recViewChats.adapter = chatsAdapter
    }

    override fun openSearchUser() {
        val intent = Intent(context, SearchActivity::class.java)
        startActivity(intent)
    }

    override fun showProgress(show: Boolean) = if (show) spinner?.visibility = View.VISIBLE else spinner.visibility = View.GONE
}