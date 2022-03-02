package com.codystritz.examplefirebaseui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codystritz.examplefirebaseui.databinding.FragmentVerifyEmailBinding
import com.codystritz.examplefirebaseui.utils.OnScreenMessages.showVerifyEmailDialog
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
        showVerifyEmailDialog(requireContext(), ::goToSignInFragment)
    }

    private fun goToSignInFragment() {
        val action = VerifyEmailFragmentDirections
            .actionVerifyEmailFragmentToSignInFragment()
        findNavController().navigate(action)
    }

}