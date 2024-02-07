package id.motionlab.firebaseexplore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import id.motionlab.firebaseexplore.features.authentication.LoginActivity
import id.motionlab.firebaseexplore.features.firestore.ComplaintActivity
import id.motionlab.firebaseexplore.features.storage.UploadActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firebaseAuthenticationBtn = findViewById<Button>(R.id.authentication_btn)
        val firebaseFirestoreBtn = findViewById<Button>(R.id.firestore_btn)
        val firebaseStorageBtn = findViewById<Button>(R.id.firestorage_btn)

        firebaseAuthenticationBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
        firebaseFirestoreBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, ComplaintActivity::class.java))
        }
        firebaseStorageBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, UploadActivity::class.java))
        }
    }
}