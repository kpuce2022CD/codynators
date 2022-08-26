package com.example.features.main.home.presentation

import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.base.BaseFragment
import com.example.codrum.R
import com.example.codrum.databinding.FragmentHomeBinding
import com.example.features.intro.presentation.LoadingDialog
import com.example.features.main.data.dto.Song
import com.example.features.main.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


const val INTERVAL_TIME = 3000L

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    // for Banner
    private var bannerPosition = Int.MAX_VALUE / 2
    private lateinit var job: Job

    // for Record
    private val recorder = MediaRecorder()
    private val sdcard = Environment.getExternalStorageDirectory()
    private val file = File(sdcard, "recorded.mp4")
    private var filename = file.absolutePath

    private val musicAdapter = MusicAdapter(itemClickListener = {
        doOnClick(it)
    })
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var loadingDialog: LoadingDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog(requireContext())
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
            rvHomeSongList.adapter = musicAdapter
        }
    }

    private fun showLoading(loading: Boolean) =
        if (loading) loadingDialog.show() else loadingDialog.dismiss()


    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.practiceSong.collect { songList ->
                        musicAdapter.submitList(songList.toList())
                    }
                }
                launch {
                    viewModel.isLoading.collect { state ->
                        showLoading(state)
                    }
                }
            }
        }
    }

    private fun doOnClick(item: Song) {
        AlertDialog.Builder(requireActivity())
            .setTitle(item.filename)
            .setPositiveButton("시작하기") { _, _ ->
                if (binding.switchRecord.isChecked) {
                    // TODO 유니티 연결하기 && 녹음
                    /*startRecord()*/
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
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

}/* : Fragment() {


    private var recordFlag = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initPager()
        loadingDialog = LoadingDialog(requireActivity())
        binding.itemListMusic.adapter = adapter
        subscribeToObservables()
        return binding.root
    }

    private fun initPager() {
        binding.viewpager.apply {
            adapter = InfiniteAdapter(getBannerList())
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setCurrentItem(currentPosition, false)
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
        stopRecord()
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
                if (binding.switchRecord.isChecked) {
                    startRecord()
                }
            }.setNegativeButton("취소") { _, _ ->
            }.setNeutralButton("삭제하기") { _, _ ->
                viewModel.deleteSong(item)
            }
            .create()
            .show()
    }

    private fun startRecord() {
        initRecorder(recorder)

        recorder.setOutputFile(filename)
        runCatching {
            recorder.prepare()
        }.onSuccess {
            recorder.start()
            recordFlag = true
        }.onFailure { e ->
            Log.d("record", "record: $e")
        }
    }

    private fun initRecorder(recorder: MediaRecorder) {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
    }

    private fun stopRecord() {
        if (recordFlag) {
            recorder.apply {
                stop()
                release()
            }
            recordFlag = false
        }
    }
}
*/