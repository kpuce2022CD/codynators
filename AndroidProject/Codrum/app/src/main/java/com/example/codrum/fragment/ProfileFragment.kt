package com.example.codrum.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.codrum.adapter.MusicAdapter
import com.example.codrum.data.Music
import com.example.codrum.databinding.FragmentProfileBinding
import com.example.codrum.dialog.LoadingDialog
import com.example.codrum.viewModel.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private val adapter = MusicAdapter(itemClickListener = {
        // putExtra로 유니티 넘겨주기
    })

    val userUID = Firebase.auth.currentUser?.uid.toString()

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadingDialog = LoadingDialog(requireActivity())
        viewModel.renewSongList(userUID)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.itemListMusic.adapter = adapter
        binding.btnRenew.setOnClickListener {
            viewModel.renewSongList(userUID)
        }
        subscribeToObservables()
        return binding.root
    }

    private fun subscribeToObservables() {
        lifecycleScope.launch {
            viewModel.songList.collect() { song ->
                adapter.submitList(song.toList())
            }
        }
        viewModel.isLoading.observe(requireActivity()) { loading ->
            showLoading(loading)
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) loadingDialog.show() else loadingDialog.dismiss()
    }

}