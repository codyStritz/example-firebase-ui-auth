package com.codystritz.examplefirebaseui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.fragment.findNavController
import com.codystritz.examplefirebaseui.R
import com.codystritz.examplefirebaseui.databinding.FragmentSignInBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.codystritz.examplefirebaseui.utils.OnScreenMessages.showToast


class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private val signIn: ActivityResultLauncher<Intent> =
        registerForActivityResult(FirebaseAuthUIActivityResultContract(), this::onSignInResult)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // IF there is no signed in user, launch FirebaseUI
        // Otherwise, head to Home Fragment
        when {
            auth.currentUser == null -> {
                val signInIntent = getSignInIntent()
                signIn.launch(signInIntent) // Launch FirebaseUI Sign In
            }
            auth.currentUser!!.isEmailVerified -> {
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            }
            else -> {
                handleUnverifiedEmail()
            }
        }
    }

    private fun getSignInIntent(): Intent {
        // Build Intent with sign in options for FirebaseUI
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setIsSmartLockEnabled(false)
            .setLogo(R.mipmap.ic_launcher_round)
            .setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
            )
            .build()
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response: IdpResponse? = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            Log.d(TAG, "Sign in successful!")
            if (response?.isNewUser == true) {
                if (auth.currentUser != null) {
                    if (!auth.currentUser!!.isEmailVerified) {
                        val newUser = auth.currentUser
                        sendVerificationEmail(newUser!!)
                    } else {
                        goToHomeFragment()
                    }
                }
            } else {
                if (auth.currentUser != null) {
                    if (auth.currentUser!!.isEmailVerified) {
                        goToHomeFragment()
                    } else {                                        // Email not verified
                        handleUnverifiedEmail()
                    }
                }
            }
        } else {
            requireActivity().finish()
            if (response == null) {
                Log.w(TAG, "Sign in canceled")
            } else {
                Log.w(TAG, "Sign in error", response.error)
            }
        }
    }

    private fun handleUnverifiedEmail() {
        val signOutTask: Task<Void> = AuthUI.getInstance().signOut(requireContext())
        signOutTask.addOnSuccessListener {
            val action = SignInFragmentDirections.actionSignInFragmentToVerifyEmailFragment()
            findNavController().navigate(action)
        }
        .addOnCanceledListener {
            Log.d(TAG, "Sign Out Unsuccessful")
        }
        .addOnFailureListener {
            Log.d(TAG, "Sign Out Error: " + it.message)
        }
    }

    private fun sendVerificationEmail(firebaseUser: FirebaseUser) {
        firebaseUser.sendEmailVerification()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    handleUnverifiedEmail()
                } else {
                    showToast(requireContext(), "Error")
                }
            }
            .addOnCanceledListener {
                showToast(requireContext(), "Email not sent")
            }
    }

    private fun goToHomeFragment() {
        findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
    }

    companion object {
        private const val TAG = "SignInFragment"
    }
}