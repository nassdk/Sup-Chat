package com.nassdk.supchat.presentation.profilescreen.mvp

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.example.domain.model.User
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.nassdk.supchat.global.BasePresenter
import java.util.*

@Suppress("INACCESSIBLE_TYPE")
@InjectViewState
class ProfilePresenter : BasePresenter<ProfileView>() {

    private lateinit var reference: DatabaseReference
    private lateinit var storage: StorageReference
    private var sTask: StorageTask<*>? = null

    fun fetchData(userId: String = "") {

        reference = if (userId.isEmpty())
            FirebaseDatabase.getInstance().getReference("Users").child(fbUser?.uid ?: "0")
        else
            FirebaseDatabase.getInstance().getReference("Users").child(userId.toString())

        viewState.showLoading()
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                viewState.hideLoading()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)

                user?.let { viewState.showData(user = user) }
                viewState.hideLoading()
            }
        })
    }

    fun uploadImage(imageUri: Uri) {

        storage = FirebaseStorage.getInstance().getReference("Uploads")
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fbUser?.uid ?: "0")

        viewState.showLoading()
        viewState.enablePhotoFab(enable = false)
        viewState.uploadInProgress()

        val fileReference = storage.child(System.currentTimeMillis().toString())

        sTask = fileReference.putFile(imageUri)
        sTask!!.continueWithTask { task ->
            if (!task.isSuccessful) {
                throw task.exception!!
            }

            fileReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val mUri = downloadUri!!.toString()

                reference = FirebaseDatabase.getInstance().getReference("Users").child(fbUser?.uid
                        ?: "0")
                val map = HashMap<String, Any>()
                map["imageURL"] = "" + mUri
                reference.updateChildren(map)
                viewState.hideLoading()
                viewState.enablePhotoFab(enable = true)
            }

        }.addOnFailureListener { e ->
            viewState.hideLoading()
            viewState.enablePhotoFab(enable = true)
            viewState.showFailError(e)
        }
    }
}