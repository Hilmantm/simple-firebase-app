package id.motionlab.firebaseexplore.features.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.motionlab.firebaseexplore.R


class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val loginBtn = findViewById<Button>(R.id.login_btn)

        auth = Firebase.auth
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_oauth_serverid))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this@LoginActivity, googleSignInOptions);

        loginBtn.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 100)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val toMain = Intent(this@LoginActivity, HomeActivity::class.java)
            toMain.putExtra(HomeActivity.EXTRA_NAME, currentUser.displayName)
            toMain.putExtra(HomeActivity.EXTRA_EMAIL, currentUser.email)
            startActivity(toMain)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> {
                val signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
                if (signInAccountTask.isSuccessful) {
                    try {
                        val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
                        if (googleSignInAccount != null) {
                            val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
                            auth.signInWithCredential(authCredential).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val currentUser = it.result.user
                                    val toMain = Intent(this@LoginActivity, HomeActivity::class.java)
                                    toMain.putExtra(HomeActivity.EXTRA_NAME, currentUser?.displayName)
                                    toMain.putExtra(HomeActivity.EXTRA_EMAIL, currentUser?.email)
                                    startActivity(toMain)
                                    finish()
                                }
                            }
                        }
                    } catch (e: ApiException) {
                        Log.e("LoginActivity", "onActivityResult: ${e.message}")
                    }
                }
            }
        }
    }
}