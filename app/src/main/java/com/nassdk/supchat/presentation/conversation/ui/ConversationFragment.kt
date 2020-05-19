package com.nassdk.supchat.presentation.conversation.ui

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.example.domain.model.Chat
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.domain.extensions.toTextString
import com.nassdk.supchat.domain.extensions.toast
import com.nassdk.supchat.domain.global.BaseFragment
import com.nassdk.supchat.presentation.conversation.adapter.MessageAdapter
import com.nassdk.supchat.presentation.conversation.mvp.ConversationPresenter
import com.nassdk.supchat.presentation.conversation.mvp.ConversationView
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*

class ConversationFragment : BaseFragment(), ConversationView, View.OnClickListener {

    override val resourceLayout = R.layout.activity_chat

    @InjectPresenter
    lateinit var presenter: ConversationPresenter

    private lateinit var userId: String

    private val adapter: MessageAdapter    by lazy { MessageAdapter() }
    private val fireBaseUser: FirebaseUser by lazy { FirebaseAuth.getInstance().currentUser!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments!!.getString("userId") ?: "0"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBottomNavigation(show = false)
        init()
    }

    private fun init() {
        presenter.fetchData(userId = userId)
        recViewChats.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomNavigation(show = true)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.butSendMessage -> {

                if (etMessage.toTextString().isNotEmpty())
                    if (!isNetworkAvailable(activity!!)) showNoInternetDialog()
                    else presenter.sendMessage(fireBaseUser.uid, userId, etMessage.toTextString())

                etMessage.setText("")
            }
            R.id.tvUsername     -> {}
        }
    }

    override fun showBottomNavigation(show: Boolean) = getBaseFragment().showBottomNavigation(show = false)

    override fun onBackPressed() = presenter.onBackPressed()
    override fun showEmptyError() = toast(getString(R.string.conversation_screen_empty_message_error_message))
    override fun setupMessages(listOfChats: ArrayList<Chat>) = adapter.setData(listOfChats)
    override fun setData(user: User) {

        tvUsername.text = user.userName
        if (user.imageURL == IMAGE_DEFAULT_STATE) profileImage_Chat.setImageResource(R.mipmap.ic_launcher_round)
        else {
            Glide
                    .with(this)
                    .load(user.imageURL)
                    .into(profileImage_Chat)
        }
    }

    companion object {
        const val IMAGE_DEFAULT_STATE = "default"
    }
}