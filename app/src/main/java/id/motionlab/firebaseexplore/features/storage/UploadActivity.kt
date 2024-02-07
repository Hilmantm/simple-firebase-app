package id.motionlab.firebaseexplore.features.storage

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage
import id.motionlab.firebaseexplore.R
import java.util.UUID

class UploadActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        val chooseImageBtn = findViewById<Button>(R.id.choose_image_btn)
        storage = Firebase.storage("gs://testing-project-242218.appspot.com")

        chooseImageBtn.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> {
                if (data != null && resultCode == Activity.RESULT_OK) {
                    val selectedImage = data.data
                    val storageRef = storage.reference
                    val fileName = UUID.randomUUID()
                    val profilePict = storageRef.child("profile/$fileName").putFile(selectedImage!!).addOnSuccessListener {
                        Toast.makeText(
                            this@UploadActivity,
                            "success upload image",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener {
                        Toast.makeText(this@UploadActivity, it.message, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
}