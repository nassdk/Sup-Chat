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
import com.nassdk.supchat.R
import com.nassdk.supchat.model.User
import com.nassdk.supchat.presentation.profilescreen.mvp.ProfilePresenter
import com.nassdk.supchat.presentation.profilescreen.mvp.ProfileView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_chats.spinner
import java.lang.Exception

@Suppress("INACCESSIBLE_TYPE")
class ProfileActivity : MvpAppCompatActivity(), ProfileView {

    private lateinit var fbUser: FirebaseUser
    private lateinit var reference: DatabaseReference
    private lateinit var storage: StorageReference
    private var sTask: StorageTask<*>? = null

    private lateinit var imageUri: Uri

    private val IMAGE_REQUEST = 1

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initViews()
    }

    private fun initViews() {
        ibProfile_back.setOnClickListener {
            finish()
        }

        storage = FirebaseStorage.getInstance().getReference("Uploads")
        fbUser = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fbUser.uid)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)

                if (user != null) {
                    tvProfile_Name.text = user.userName

                    if (user.imageURL == "default") {
                        ivProfile_Image.setImageResource(R.mipmap.ic_launcher_round)
                    } else {
                        Glide
                                .with(applicationContext)
                                .load(user.imageURL)
                                .into(ivProfile_Image)
                    }
                }
            }
        })
        fab_photo.setOnClickListener {
            if (presenter.checkInternetConnection(context = applicationContext)) {
            } else {
                openImage()
            }
        }
    }


    override fun showNoImageError() {
        Toast.makeText(applicationContext, "No Image Selected", Toast.LENGTH_SHORT).show()
    }

    override fun showFailError(error: Exception) {
        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun showProgressError() {
        Toast.makeText(applicationContext, "Uploading in Progress", Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        spinner.visibility = View.VISIBLE
        fab_photo.isEnabled = false
    }

    override fun hideProgress() {
        spinner.visibility = View.GONE
        fab_photo.isEnabled = true
    }

    override fun uploadInProgress() {
        Toast.makeText(applicationContext, "Upload in Progress", Toast.LENGTH_SHORT).show()
    }

    override fun showDialog() {
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
                Toast.makeText(applicationContext, "Upload in Progress", Toast.LENGTH_SHORT).show()
            } else {
                presenter.uploadImage(imageUri)
            }
        }
    }
}