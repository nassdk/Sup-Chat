package com.example.firabasefirstexperience.kotlin.activity

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.firabasefirstexperience.R
import com.example.firabasefirstexperience.java.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.HashMap

class ProfileActivity : AppCompatActivity() {

    private var fbUser: FirebaseUser? = null
    private var reference: DatabaseReference? = null
    private var storage: StorageReference? = null
    private var sTask: StorageTask<*>? = null

    private var imageUri: Uri? = null

    private val IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ibProfile_back.setOnClickListener {
            finish()
        }

        storage = FirebaseStorage.getInstance().getReference("Uploads")
        fbUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fbUser!!.uid)

        reference?.addValueEventListener(object : ValueEventListener {
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
            openImage()
        }
    }

    private fun openImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_REQUEST)
    }

    private fun getFileExtension(uri: Uri): String? {
        val contentResolver: ContentResolver = applicationContext.contentResolver
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }


    private fun uploadImage() {

        if (imageUri != null) {
            val fileReference = storage?.child(System.currentTimeMillis().toString()
                    + "." + getFileExtension(imageUri!!))

            sTask = fileReference?.putFile(imageUri!!)
            sTask?.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }

                fileReference?.downloadUrl
            }?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val mUri = downloadUri!!.toString()

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(fbUser!!.uid)
                    val map = HashMap<String, Any>()
                    map["imageURL"] = "" + mUri
                    reference?.updateChildren(map)

                } else {
                    Toast.makeText(applicationContext, "Failed!", Toast.LENGTH_SHORT).show()

                }
            }?.addOnFailureListener { e ->
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()

            }
        } else run { Toast.makeText(applicationContext, "No image selected", Toast.LENGTH_SHORT).show() }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data

            if(sTask != null && sTask!!.isInProgress) {
                Toast.makeText(applicationContext, "Upload in Progress", Toast.LENGTH_SHORT).show()
            } else {
                uploadImage()
            }
        }
    }
}
