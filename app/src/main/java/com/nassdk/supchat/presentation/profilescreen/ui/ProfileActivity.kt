package com.nassdk.supchat.presentation.profilescreen.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageTask
import com.nassdk.supchat.R
import com.nassdk.supchat.domain.extensions.isNetworkAvailable
import com.nassdk.supchat.domain.model.User
import com.nassdk.supchat.presentation.profilescreen.mvp.ProfilePresenter
import com.nassdk.supchat.presentation.profilescreen.mvp.ProfileView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_chats.spinner

@Suppress("INACCESSIBLE_TYPE")
class ProfileActivity : MvpAppCompatActivity(), ProfileView {

    private var sTask: StorageTask<*>? = null

    private lateinit var imageUri: Uri

    private val IMAGE_REQUEST = 1

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        presenter.fetchData()

        initViews()
    }

    private fun initViews() {

        ibProfile_back.setOnClickListener {
            finish()
        }

        fab_photo.setOnClickListener {
            if (!isNetworkAvailable(context = this@ProfileActivity)) {
                showNoInternetDialog()
            } else {
                openImage()
            }
        }
    }

    override fun showNoImageError() = Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show()

    override fun showFailError(error: Exception) = Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()

    override fun showProgressError() = Toast.makeText(this, "Uploading in Progress", Toast.LENGTH_SHORT).show()

    override fun showProgress(show: Boolean) = if (show) spinner.visibility = View.VISIBLE else spinner.visibility = View.GONE

    override fun uploadInProgress() = Toast.makeText(this, "Upload in Progress", Toast.LENGTH_SHORT).show()

    override fun enablePhotoFab(enable: Boolean) {
        fab_photo.isEnabled = enable
    }

    override fun showData(user: User) {

        tvProfile_Name.text = user.userName

        if (user.imageURL == "default") {
            ivProfile_Image.setImageResource(R.mipmap.ic_launcher_round)
        } else {
            Glide
                    .with(this)
                    .load(user.imageURL)
                    .into(ivProfile_Image)
        }
    }

    override fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(this@ProfileActivity)
        builder.setTitle("Warning!")
                .setMessage("Your device is not connected to Internet. Please, try later")
                .setIcon(R.drawable.ic_warning)
                .setCancelable(false)
                .setNegativeButton("Exit"
                ) { _, _ -> finish() }
        val alert = builder.create()
        alert.show()
    }

    private fun openImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!

            if (sTask != null && sTask?.isInProgress!!) {
                Toast.makeText(this, "Upload in Progress", Toast.LENGTH_SHORT).show()
            } else {
                presenter.uploadImage(imageUri)
            }
        }
    }
}