package id.motionlab.firebaseexplore.features.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.motionlab.firebaseexplore.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val welcomingText = findViewById<TextView>(R.id.welcoming_text)
        val signOutBtn = findViewById<Button>(R.id.logout_btn)

        val displayName = intent.extras?.getString(EXTRA_NAME)
        val email = intent.extras?.getString(EXTRA_EMAIL)

        welcomingText.text = "Welcome ${displayName} with ${email}"
        signOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            val toLogin = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(toLogin)
            finish()
        }

    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
    }
}