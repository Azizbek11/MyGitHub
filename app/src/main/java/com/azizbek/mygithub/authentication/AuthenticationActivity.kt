package com.azizbek.mygithub.authentication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.ProgressBar
import android.os.Bundle
import com.azizbek.mygithub.R
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.AuthResult
import android.widget.Toast
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import com.azizbek.mygithub.MainActivity
import java.lang.Exception
import java.util.*

class AuthenticationActivity : AppCompatActivity() {
    private var userName: String? = null
    private var email: String? = null
    private var photoUri: String? = null
    private var mAuth: FirebaseAuth? = null
    private var firebaseUser: FirebaseUser? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        mAuth = FirebaseAuth.getInstance()
        supportActionBar!!.hide()
        progressBar = findViewById(R.id.progressbar)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseUser = FirebaseAuth.getInstance().currentUser
        userProfile
    }

    private fun authWithGithub() {
        val mAuth = FirebaseAuth.getInstance()
        val provider = OAuthProvider.newBuilder("github.com")
        mAuth
                .startActivityForSignInWithProvider(this@AuthenticationActivity, provider.build())
                .addOnSuccessListener { authResult: AuthResult ->

                    val githubUser = authResult.additionalUserInfo?.username
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    userProfile
                    val sharedPreferences:SharedPreferences = this.getSharedPreferences("githubname", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("githubUserName", githubUser)
                    editor.apply()
                }
                .addOnFailureListener { e: Exception? ->
                    // Handle failure.
                    Toast.makeText(this, "Fail$e", Toast.LENGTH_SHORT).show()
                }
    }

    private val userProfile: Unit
        get() {
            firebaseUser = FirebaseAuth.getInstance().currentUser
            if (firebaseUser != null) {
                progressBar!!.visibility = View.INVISIBLE
                userName = firebaseUser!!.displayName
                email = firebaseUser!!.email
                photoUri = firebaseUser!!.photoUrl.toString()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("username", userName)
                intent.putExtra("email", email)
                intent.putExtra("photoUrl", photoUri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                this.startActivity(intent)
                finish()
            }
        }

    fun goNextPage(view: View?) {
        progressBar!!.visibility = View.VISIBLE
        authWithGithub()
    }

    companion object {
        private const val TAG = "AuthenticationActivity"
    }
}