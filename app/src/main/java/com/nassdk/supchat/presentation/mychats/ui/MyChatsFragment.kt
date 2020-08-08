package com.nassdk.supchat.presentation.mychats.ui

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.domain.model.User
import com.nassdk.supchat.R
import com.nassdk.supchat.global.BaseFragment
import com.nassdk.supchat.presentation.mychats.mvp.MyChatsPresenter
import com.nassdk.supchat.presentation.mychats.mvp.MyChatsView
import kotlinx.android.synthetic.main.screen_my_chats.*

class MyChatsFragment : BaseFragment(), MyChatsView {

    override val resourceLayout: Int = R.layout.screen_my_chats

    @InjectPresenter
    lateinit var presenter: MyChatsPresenter

    private val adapter: ChatsAdapter by lazy {
        ChatsAdapter(
                chatClickListener = { presenter.toConversationWith(userId = it) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        searchFab.setOnClickListener { presenter.toSearch() }
    }

    override fun setAdapter(list: ArrayList<User>) {

        adapter.setData(list)
        recViewChats.adapter = adapter
    }

    override fun onBackPressed() = presenter.onBackPressed()
}