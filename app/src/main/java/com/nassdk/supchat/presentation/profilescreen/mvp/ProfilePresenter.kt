package com.nassdk.supchat.presentation.profilescreen.mvp

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.nassdk.supchat.domain.global.BasePresenter
import java.util.*

@Suppress("INACCESSIBLE_TYPE")
@InjectViewState
class ProfilePresenter : BasePresenter<ProfileView>() {

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var reference: DatabaseReference
    private lateinit var storage: StorageReference
    private var sTask: StorageTask<*>? = null

    fun fetchData() {

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)

                if (user != null) {
                    viewState.showData(user = user)
                }
            }
        })
    }

    fun uploadImage(imageUri: Uri) {

        storage = FirebaseStorage.getInstance().getReference("Uploads")
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
        if (imageUri != null) {
            viewState.showProgress(show = true)
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

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
                    val map = HashMap<String, Any>()
                    map["imageURL"] = "" + mUri
                    reference.updateChildren(map)
                    viewState.showProgress(show = false)
                    viewState.enablePhotoFab(enable = true)
                } else {

                }
            }.addOnFailureListener { e ->
                viewState.showProgress(show = false)
                viewState.enablePhotoFab(enable = true)
                viewState.showFailError(e)
            }
        } else run {
            viewState.showProgress(show = false)
            viewState.enablePhotoFab(enable = true)
            viewState.showNoImageError()
        }
    }
}