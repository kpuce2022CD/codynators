package com.example.features.main.upload.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.base.BaseFragment
import com.example.codrum.R
import com.example.codrum.databinding.FragmentUploadBinding
import com.example.features.main.data.dto.Song
import com.example.features.main.data.dto.Song.Companion.CUSTOM
import com.example.features.main.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UploadFragment : BaseFragment<FragmentUploadBinding>(R.layout.fragment_upload) {

    private var pickImageFromAlbum = 0
    private lateinit var uriPhoto: Uri

    private val mainViewModel: MainViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectFlow()
        initView()
    }

    private fun initView() {
        binding.apply {
            ivElbum.setOnClickListener {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, pickImageFromAlbum)
            }
            btnUpload.setOnClickListener {
                mainViewModel.upload(Song(CUSTOM, etSongTitle.text.toString()), uriPhoto)
            }
        }
    }

    private fun collectFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mainViewModel.isSuccess.collect { state ->
                        if (state) Toast.makeText(requireContext(), "업로드 성공", Toast.LENGTH_SHORT)
                            .show()
                        else Toast.makeText(requireContext(), "업로드 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageFromAlbum) {
            if (resultCode == Activity.RESULT_OK) {
                uriPhoto = data?.data!!
                binding.apply {
                    ivElbum.setImageURI(uriPhoto)
                    if (ContextCompat.checkSelfPermission(
                            binding.root.context,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        etSongTitle.isVisible = true
                    }
                }
            } else {
                Toast.makeText(requireContext(), "다시 작업해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}