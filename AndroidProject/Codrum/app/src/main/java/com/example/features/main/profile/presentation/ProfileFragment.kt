package com.example.features.main.profile.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.base.BaseFragment
import com.example.codrum.R
import com.example.codrum.databinding.FragmentProfileBinding
import com.example.features.intro.presentation.LoadingDialog
import com.example.features.main.data.dto.Song
import com.example.features.main.presentation.MainViewModel
import com.example.features.main.presentation.SongAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private val mySongAdapter = SongAdapter(itemClickListener = {
        doOnClick(it)
    })
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var loadingDialog: LoadingDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog(requireContext())
        collectFlow()
        initView()
    }

    private fun initView() {
        binding.apply {
            btnAddSong.setOnClickListener {
                it.findNavController().navigate(R.id.uploadFragment)
            }
            rvListMySong.adapter = mySongAdapter
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.apply {
                    launch {
                        mySong.collectLatest { songList ->
                            mySongAdapter.submitList(songList.toList())
                            binding.tvNothing.isVisible = songList.isEmpty()
                        }
                    }
                    launch {
                        isLoading.collect { state ->
                            showLoading(state)
                        }
                    }
                    launch {
                        isDelete.collect { state ->
                            Toast.makeText(
                                requireContext(),
                                if (state) "삭제 성공" else "삭제 실패",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) loadingDialog.show() else loadingDialog.dismiss()
    }

    private fun doOnClick(item: Song) {
        AlertDialog.Builder(requireActivity())
            .setTitle(item.filename)
            .setPositiveButton("시작하기") { _, _ ->
                if (binding.switchMySongRecord.isChecked) {
                    // TODO 유니티 연결하기 && 녹음
                    /*startRecord()*/
                } else {
                    // TODO 유니티만 연결
                }
            }.setNegativeButton("취소") { _, _ -> }
            .setNeutralButton("삭제하기") { _, _ ->
                mainViewModel.deleteSong(item)
            }
            .create()
            .show()
    }
}