package com.example.features.intro.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.base.BaseFragment
import com.example.codrum.R
import com.example.codrum.databinding.FragmentSignInBinding
import com.example.codrum.dialog.LoadingDialog
import com.example.codrum.view.MainActivity
import com.example.shared.check
import kotlinx.coroutines.launch

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    private val introViewModel: IntroViewModel by activityViewModels()
    private lateinit var dialog: LoadingDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = LoadingDialog(requireContext())
        collectFlow()
        initView()
    }

    private fun initView() {
        binding.apply {
            btnSignUp.setOnClickListener {
                it.findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
            btnSignIn.apply {
                setOnClickListener {
                    if (check(tlSignInEmail, tlSignInPasswd)) {
                        introViewModel.signIn(
                            tlSignInEmail.editText?.text.toString(),
                            tlSignInPasswd.editText?.text.toString()
                        )
                    }
                }
            }
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    introViewModel.isLoading.collect { isLoading ->
                        if (isLoading) dialog.show()
                        else dialog.dismiss()
                    }
                }
                launch {
                    introViewModel.isSuccess.collect { isSuccess ->
                        if (isSuccess) {
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                            requireActivity().finish()
                        } else {
                            Toast.makeText(requireActivity(), "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}