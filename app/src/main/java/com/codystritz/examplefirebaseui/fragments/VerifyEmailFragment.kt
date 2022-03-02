package com.codystritz.examplefirebaseui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.codystritz.examplefirebaseui.R
import com.codystritz.examplefirebaseui.databinding.FragmentVerifyEmailBinding
import com.google.firebase.auth.FirebaseAuth


class VerifyEmailFragment : Fragment() {
    private var _binding: FragmentVerifyEmailBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVerifyEmailBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showVerifyEmailDialog()
    }

    private fun showVerifyEmailDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Notice")
            .setMessage("Please verify your email before signing in")
            .setPositiveButton("Okay") {_,_ -> goToSignInFragment()}
            .create()
            .show()
    }

    private fun goToSignInFragment() {
        val action = VerifyEmailFragmentDirections
            .actionVerifyEmailFragmentToSignInFragment()
        findNavController().navigate(action)
    }

}