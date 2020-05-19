package com.nassdk.supchat.presentation.diffprofile.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.example.domain.model.User
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.presentation.diffprofile.mvp.DiffProfilePresenter
import com.nassdk.supchat.presentation.diffprofile.mvp.DiffProfileView
import kotlinx.android.synthetic.main.activity_diff_profile.*

class DiffProfileActivity : MvpAppCompatActivity(), DiffProfileView, View.OnClickListener {

    private var id = ""

    @InjectPresenter
    lateinit var presenter: DiffProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_profile)

        initViews()
    }

    private fun initViews() {
        val intent = intent
        id = intent.getStringExtra("id")!!

        presenter.fetchData(userId = id)
        ibDifProfile_back.setOnClickListener(this)
        fab_message.setOnClickListener(this)
    }

    override fun openChat() {
//        val intentToChatActivity = Intent(this, ConversationActivity::class.java)
//        intentToChatActivity.putExtra("userId", id)
//        startActivity(intentToChatActivity)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.ibDifProfile_back -> finish()
            R.id.fab_message -> {
                if (!isNetworkAvailable(context = this@DiffProfileActivity)) {
                    showNoInternetDialog()
                } else {
                    presenter.toChat()
                }
            }
        }
    }

    override fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(this@DiffProfileActivity)
        builder.setTitle("Warning!")
                .setMessage("Your device is not connected to Internet. Please, try later")
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setNegativeButton("Exit"
                ) { _, _ -> finish() }
        val alert = builder.create()
        alert.show()
    }

    override fun showData(user: User) {
        tvDifProfile_Name.text = user.userName

        if (!user.status.equals("offline")) {
            tvDifProfile_Status.visibility = View.VISIBLE
        } else {
            tvDifProfile_Status.visibility = View.GONE
        }

        if (user.imageURL == "default") {
            ivDifProfile_Image.setImageResource(R.mipmap.ic_launcher_round)
        } else {
            Glide
                    .with(this)
                    .load(user.imageURL)
                    .into(ivDifProfile_Image)
        }
    }
}