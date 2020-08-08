package com.nassdk.supchat.presentation.profilescreen.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.example.domain.model.User
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.storage.StorageTask
import com.nassdk.supchat.R
import com.nassdk.supchat.global.extensions.toast
import com.nassdk.supchat.global.BaseFragment
import com.nassdk.supchat.global.extensions.withArgs
import com.nassdk.supchat.presentation.conversation.ui.ConversationFragment
import com.nassdk.supchat.presentation.profilescreen.mvp.ProfilePresenter
import com.nassdk.supchat.presentation.profilescreen.mvp.ProfileView
import kotlinx.android.synthetic.main.screen_profile.*
import kotlin.math.abs


class ProfileFragment : BaseFragment(), ProfileView, AppBarLayout.OnOffsetChangedListener {

    override val resourceLayout: Int = R.layout.screen_profile

    private var mIsTheTitleVisible = false
    private var mIsTheTitleContainerVisible = true

    private var sTask: StorageTask<*>? = null

    private lateinit var imageUri: Uri

    private var userId: String? = null

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        userId = arguments?.getString(USER_IDENTIFIER, "")
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        presenter.fetchData(userId = userId ?: "")
    }

    private fun initViews() {

        mainAppbar.addOnOffsetChangedListener(this)
        startAlphaAnimation(userNameToolbar, 0, View.INVISIBLE)
    }


    private fun handleToolbarTitleVisibility(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(userNameToolbar, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
                profileToolbar.setBackgroundColor(ContextCompat.getColor(context!!, R.color.default_pink_color))
                mIsTheTitleVisible = true
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(userNameToolbar, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
                profileToolbar.setBackgroundColor(ContextCompat.getColor(context!!, R.color.default_background_color))
                mIsTheTitleVisible = false
            }
        }
    }

    private fun handleAlphaOnTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(userInfoContainer, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
                mIsTheTitleContainerVisible = false
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(userInfoContainer, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
                mIsTheTitleContainerVisible = true
            }
        }
    }

    private fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {
        val alphaAnimation = if (visibility == View.VISIBLE)
            AlphaAnimation(0f, 1f)
        else
            AlphaAnimation(1f, 0f)

        alphaAnimation.duration = duration
        alphaAnimation.fillAfter = true
        v.startAnimation(alphaAnimation)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val maxScroll = appBarLayout!!.totalScrollRange
        val percentage = abs(verticalOffset) / maxScroll.toFloat()

        handleAlphaOnTitle(percentage)
        handleToolbarTitleVisibility(percentage)
    }

    override fun onBackPressed() = presenter.onBackPressed()
    override fun showNoImageError() = toast(getString(R.string.profile_no_image_selected_error_text))
    override fun showFailError(error: Exception) = toast(error.toString())
    override fun showProgressError() = toast(getString(R.string.profile_loading_progress_message))
    override fun uploadInProgress() = toast(getString(R.string.profile_progress_upload_message))
    override fun enablePhotoFab(enable: Boolean) {}

    override fun showData(user: User) {

        userName.text = user.userName
        userNameToolbar.text = user.userName

        if (user.imageURL == IMAGE_DEFAULT_STATE)
            profileAvatar.setImageResource(R.mipmap.ic_launcher_round)
        else
            Glide
                    .with(this)
                    .load(user.imageURL)
                    .into(profileAvatar)
    }

    private fun openImage() {
        val intent = Intent()
        intent.type = IMAGE_TYPE
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            imageUri = data.data!!

            if (sTask != null && sTask?.isInProgress!!)
                toast(getString(R.string.profile_progress_upload_message))
            else
                presenter.uploadImage(imageUri)
        }
    }

    companion object {

        fun newInstance(userId: String) = ProfileFragment().withArgs {
            userId.run {
                putString(USER_IDENTIFIER, userId)
            }
        }

        const val USER_IDENTIFIER = "USER_IDENTIFIER"
        const val IMAGE_REQUEST_CODE = 1
        const val IMAGE_DEFAULT_STATE = "default"
        const val IMAGE_TYPE = "image/*"
        const val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f
        const val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f
        const val ALPHA_ANIMATIONS_DURATION = 200
    }
}