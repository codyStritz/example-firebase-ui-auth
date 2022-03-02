package com.codystritz.examplefirebaseui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codystritz.examplefirebaseui.databinding.FragmentHomeBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emailLabel.text = auth.currentUser?.email
        binding.signOutButton.setOnClickListener { signOut() }
    }

    private fun signOut() {
        val signOutTask: Task<Void> = AuthUI.getInstance().signOut(requireContext())
        signOutTask.addOnSuccessListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSignInFragment()
            findNavController().navigate(action)
        }
            .addOnCanceledListener {
                Log.d(TAG, "Sign Out Unsuccessful")
            }
            .addOnFailureListener {
                Log.d(TAG, "Sign Out Error: " + it.message)
            }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}