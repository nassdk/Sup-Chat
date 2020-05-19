package com.nassdk.supchat.presentation.profilescreen.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.example.domain.model.User
import com.google.firebase.storage.StorageTask
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.domain.extensions.makeGone
import com.nassdk.supchat.domain.extensions.makeVisible
import com.nassdk.supchat.domain.extensions.toast
import com.nassdk.supchat.domain.global.BaseFragment
import com.nassdk.supchat.presentation.profilescreen.mvp.ProfilePresenter
import com.nassdk.supchat.presentation.profilescreen.mvp.ProfileView
import kotlinx.android.synthetic.main.screen_profile.*
import kotlinx.android.synthetic.main.screen_profile.view.*

class ProfileFragment : BaseFragment(), ProfileView {

    override val resourceLayout: Int = R.layout.screen_profile

    private var sTask: StorageTask<*>? = null

    private lateinit var imageUri: Uri

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view = view)
    }

    private fun initViews(view: View) {

        view.fab_photo.setOnClickListener {
            if (!isNetworkAvailable(context = context!!))
                showNoInternetDialog()
            else
                openImage()
        }
    }

    override fun onBackPressed()                 = presenter.onBackPressed()
    override fun showNoImageError()              = toast(getString(R.string.profile_no_image_selected_error_text))
    override fun showFailError(error: Exception) = toast(error.toString())
    override fun showProgressError()             = toast(getString(R.string.profile_loading_progress_message))
    override fun uploadInProgress()              = toast(getString(R.string.profile_progress_upload_message))

    override fun showProgress(show: Boolean)     = if (show) spinner.makeVisible() else spinner.makeGone()

    override fun enablePhotoFab(enable: Boolean) { fab_photo.isEnabled = enable }

    override fun showData(user: User) {

        tvProfile_Name.text = user.userName

        if (user.imageURL == IMAGE_DEFAULT_STATE)
            ivProfile_Image.setImageResource(R.mipmap.ic_launcher_round)
        else
            Glide
                    .with(this)
                    .load(user.imageURL)
                    .into(ivProfile_Image)
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
        val IMAGE_REQUEST_CODE  = 1
        val IMAGE_DEFAULT_STATE = "default"
        val IMAGE_TYPE          = "image/*"
    }
}