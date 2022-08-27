package com.example.features.main.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.base.BaseFragment
import com.example.codrum.R
import com.example.codrum.databinding.FragmentHomeBinding
import com.example.features.main.data.dto.Song
import com.example.features.main.presentation.MainViewModel
import com.example.features.main.presentation.SongAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


const val INTERVAL_TIME = 3000L

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    // for Banner
    private var bannerPosition = Int.MAX_VALUE / 2
    private lateinit var job: Job

    private val songAdapter = SongAdapter(itemClickListener = {
        doOnClick(it)
    })
    private val viewModel: MainViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPractice()
        collectFlow()
        initView()
    }

    private fun initView() {
        binding.apply {
            vpHomeBanner.apply {
                adapter = BannerAdapter(getBannerList())
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                setCurrentItem(bannerPosition, false)
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        bannerPosition = position
                    }
                })
            }
            rvHomeSongList.adapter = songAdapter
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.practiceSong.collect { songList ->
                    songAdapter.submitList(songList.toList())
                }
            }
        }
    }

    private fun doOnClick(item: Song) {
        AlertDialog.Builder(requireActivity())
            .setTitle(item.filename)
            .setPositiveButton("시작하기") { _, _ ->
                if (binding.switchRecord.isChecked) {
                    runCatching {
                        viewModel.recorder.startRecording()
                    }.onSuccess {
                        viewModel.recordFlag = true
                        // TODO 유니티 연결하기
                    }.onFailure {
                        Toast.makeText(requireContext(), "녹음이 불가능합니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // TODO 유니티만 연결
                }
            }.setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }

    private fun getBannerList(): List<Int> =
        listOf(R.drawable.find1, R.drawable.find2, R.drawable.start)

    private fun scrollJobCreate() {
        job = viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                delay(2000L)
                binding.vpHomeBanner.setCurrentItem(++bannerPosition, true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        scrollJobCreate()
        runCatching {
            viewModel.recorder.stopRecording(viewModel.recordFlag)
        }.onSuccess {
            viewModel.recordFlag = false
            Toast.makeText(
                requireContext(),
                "녹음이 완료되었습니다.\nSD카드에 저장되었습니다",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }
}