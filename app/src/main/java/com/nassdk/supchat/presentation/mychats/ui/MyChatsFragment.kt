package com.nassdk.supchat.presentation.mychats.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.makeGone
import com.nassdk.supchat.domain.extensions.makeVisible
import com.nassdk.supchat.domain.global.BaseFragment
import com.example.domain.model.User
import com.nassdk.supchat.presentation.mychats.mvp.MyChatsPresenter
import com.nassdk.supchat.presentation.mychats.mvp.MyChatsView
import com.nassdk.supchat.presentation.searchusers.ui.SearchActivity
import kotlinx.android.synthetic.main.screen_my_chats.*
import kotlin.collections.ArrayList

class MyChatsFragment : BaseFragment(), MyChatsView {

    override val resourceLayout: Int = R.layout.screen_my_chats

    private lateinit var navController: NavController
    @InjectPresenter
    lateinit var presenter: MyChatsPresenter

    private val adapter: ChatsAdapter by lazy {
        ChatsAdapter(chatClickListener = { openConversation(id = it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view = view)
    }

    private fun initViews(view: View) {
        fabStartNewChat.setOnClickListener { presenter.toSearch() }
        navController = Navigation.findNavController(view)
    }

    private fun openConversation(id: String) {}

    override fun setAdapter(list: ArrayList<User>) {
        adapter.setData(list)
        recViewChats.adapter = adapter
    }

    override fun openSearchUser() {
        val intent = Intent(context, SearchActivity::class.java)
        startActivity(intent)
    }


    override fun onBackPressed() = presenter.onBackPressed()
    override fun showProgress(show: Boolean) = if (show) spinner.makeVisible() else spinner.makeGone()
}