package com.codystritz.examplefirebaseui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codystritz.examplefirebaseui.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth and check if the user is signed in
//        auth = FirebaseAuth.getInstance()
//        if (auth.currentUser == null || auth.currentUser?.isEmailVerified == false) {
//            // Not signed in, launch the Sign In activity
//            startActivity(Intent(this, SignInActivity::class.java))
//            finish()
//            return
//        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in
//        if (auth.currentUser == null) {
//            // Not signed in, launch the Sign In activity
//            startActivity(Intent(this, SignInActivity::class.java))
//            finish()
//            return
//        }
//        binding.viewLabel.text = auth.currentUser?.email
//        binding.signOutBtn.setOnClickListener { signOut() }
    }

//    private fun signOut() {
//        val signOutTask: Task<Void> = AuthUI.getInstance().signOut(this)
//        signOutTask.addOnSuccessListener {
//            startActivity(Intent(this, SignInActivity::class.java))
//            finish()
//        }
//        signOutTask.addOnCanceledListener {
//            Log.d("MainActivity", "Sign Out Unsuccessful")
//        }
//        signOutTask.addOnFailureListener {
//            Log.d("MainActivity", "Sign Out Error: "+it.message)
//        }
//    }

}