package com.example.codrum.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.codrum.databinding.FragmentProfileBinding
import com.example.codrum.dialog.LoadingDialog
import com.example.codrum.view.adapter.MusicAdapter
import com.example.codrum.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private val adapter = MusicAdapter(itemClickListener = {
//        startActivity(Intent(requireActivity(), UnityPlayerActivity::class.java))
    })

    /* val userUID = Firebase.auth.currentUser?.uid.toString()*/
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadingDialog = LoadingDialog(requireActivity())
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.itemListMusic.adapter = adapter

        subscribeToObservables()
        return binding.root
    }

    private fun subscribeToObservables() {
        viewModel.isLoading.observe(requireActivity()) { loading ->
            showLoading(loading)
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) loadingDialog.show() else loadingDialog.dismiss()
    }

}