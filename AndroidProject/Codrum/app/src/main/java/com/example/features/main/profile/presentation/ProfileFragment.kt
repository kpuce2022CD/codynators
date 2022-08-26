package com.example.features.main.profile.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.example.base.BaseFragment
import com.example.codrum.R
import com.example.codrum.databinding.FragmentProfileBinding
import com.example.features.main.data.dto.Song
import com.example.features.main.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                // TODO 삭제하기
            }
            .create()
            .show()
    }
}/*:

    private val adapter = MusicAdapter(itemClickListener = {
//        startActivity(Intent(requireActivity(), UnityPlayerActivity::class.java))
    })

    *//* val userUID = Firebase.auth.currentUser?.uid.toString()*//*
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
*/
