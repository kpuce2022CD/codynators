package com.example.features.intro.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.App
import com.example.base.BaseFragment
import com.example.codrum.R
import com.example.codrum.databinding.FragmentSplashBinding
import com.example.codrum.view.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val DURATION: Long = 3500L

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.animationView.playAnimation()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(DURATION)
                if (App.prefs.loginCheck) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                } else {
                    requireView().findNavController()
                        .navigate(R.id.action_splashFragment_to_signInFragment)
                }
            }
        }
    }
}