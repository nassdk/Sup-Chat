package com.nassdk.supchat.presentation.profilescreen.mvp

import android.content.Context
import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.nassdk.supchat.network.isNetworkAvailable
import java.util.HashMap

@Suppress("INACCESSIBLE_TYPE")
@InjectViewState
class ProfileActivityPresenter : MvpPresenter<ProfileActivityView>() {

    private lateinit var fbUser: FirebaseUser
    private lateinit var reference: DatabaseReference
    private lateinit var storage: StorageReference
    private var sTask: StorageTask<*>? = null


    fun checkInternetConnection(context: Context) : Boolean {
        if(!isNetworkAvailable(context)) {
            viewState.showDialog()
            return true
        }
        return false
    }

    fun uploadImage(imageUri: Uri) {
        storage = FirebaseStorage.getInstance().getReference("Uploads")
        fbUser = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fbUser.uid)
        if (imageUri != null) {
            viewState.showProgress()
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

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(fbUser.uid)
                    val map = HashMap<String, Any>()
                    map["imageURL"] = "" + mUri
                    reference.updateChildren(map)
                    viewState.hideProgress()
                } else {

                }
            }.addOnFailureListener { e ->
                viewState.hideProgress()
                viewState.showFailError(e)
            }
        } else run {
            viewState.hideProgress()
            viewState.showNoImageError()
        }
    }


//    private fun getFileExtension(uri: Uri): String? {
//        val contentResolver: ContentResolver = applicationContext.contentResolver
//        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
//    }

}