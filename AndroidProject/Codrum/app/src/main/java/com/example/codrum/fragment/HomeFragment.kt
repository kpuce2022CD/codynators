package com.example.codrum.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.codrum.R
import com.example.codrum.data.Song
import com.example.codrum.databinding.FragmentHomeBinding
import com.example.codrum.dialog.LoadingDialog
import com.example.codrum.view.adapter.InfiniteAdapter
import com.example.codrum.view.adapter.MusicAdapter
import com.example.codrum.viewModel.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var currentPosition = Int.MAX_VALUE / 2
    lateinit var binding: FragmentHomeBinding
    private var myHandler = MyHandler()
    private val intervalTime = 3000.toLong()
    val userUID = Firebase.auth.currentUser?.uid.toString()

    private val adapter = MusicAdapter(itemClickListener = {
        doOnClick(it)
    })

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewpager.adapter = InfiniteAdapter(getBannerList())
        binding.viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpager.setCurrentItem(currentPosition, false)

        binding.viewpager.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    }
                }
            })
        }
        loadingDialog = LoadingDialog(requireActivity())
        binding.itemListMusic.adapter = adapter
        subscribeToObservables()
        return binding.root
    }

    private fun getBannerList(): ArrayList<Int> {
        return arrayListOf(R.drawable.find1, R.drawable.find2, R.drawable.start)
    }

    private fun autoScrollStart(intervalTime: Long) {
        myHandler.removeMessages(0) // 이거 안하면 핸들러가 1개, 2개, 3개 ... n개 만큼 계속 늘어남
        myHandler.sendEmptyMessageDelayed(0, intervalTime) // intervalTime 만큼 반복해서 핸들러를 실행하게 함
    }

    private fun autoScrollStop() {
        myHandler.removeMessages(0) // 핸들러를 중지시킴
    }

    private inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if (msg.what == 0) {
                binding.viewpager.setCurrentItem(++currentPosition, true) // 다음 페이지로 이동
                autoScrollStart(intervalTime) // 스크롤을 계속 이어서 한다.
            }
        }
    }

    // 다른 페이지 갔다가 돌아오면 다시 스크롤 시작
    override fun onResume() {
        super.onResume()
        autoScrollStart(intervalTime)
    }

    // 다른 페이지로 떠나있는 동안 스크롤이 동작할 필요는 없음. 정지
    override fun onPause() {
        super.onPause()
        autoScrollStop()
    }

    private fun subscribeToObservables() {
        viewModel.song.observe(requireActivity()) { song ->
            song?.let { adapter.submitList(song.toList()) }
        }
        viewModel.isLoading.observe(requireActivity()) { loading ->
            showLoading(loading)
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) loadingDialog.show() else loadingDialog.dismiss()
    }

    private fun doOnClick(item: Song) {
        AlertDialog.Builder(requireActivity())
            .setTitle(item.filename)
            .setPositiveButton("시작하기") { _, _ ->
                //startActivity 여기
            }.setNegativeButton("취소") { _, _ ->
            }.setNeutralButton("삭제하기") { _, _ ->
                viewModel.deleteSong(item)
            }
            .create()
            .show()
    }

}