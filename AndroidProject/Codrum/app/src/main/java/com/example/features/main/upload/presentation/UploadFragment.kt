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
import com.example.features.intro.presentation.LoadingDialog
import com.example.features.main.data.dto.Song
import com.example.features.main.data.dto.Song.Companion.CUSTOM
import com.example.features.main.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UploadFragment : BaseFragment<FragmentUploadBinding>(R.layout.fragment_upload) {

    private var pickImageFromAlbum = 0
    private lateinit var uriPhoto: Uri

    private lateinit var loadingDialog: LoadingDialog
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog(requireContext())
        collectFlow()
        initView()
    }

    private fun initView() {
        binding.apply {
            ivAlbum.setOnClickListener {
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
                mainViewModel.apply {
                    launch {
                        isSuccess.collect { state ->
                            Toast.makeText(
                                requireContext(),
                                if (state) "업로드 성공" else "업로드 실패",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    launch {
                        isLoading.collect { state ->
                            showDialog(state)
                        }
                    }
                }
            }
        }
    }

    private fun showDialog(state: Boolean) =
        if (state) loadingDialog.show() else loadingDialog.dismiss()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageFromAlbum) {
            if (resultCode == Activity.RESULT_OK) {
                uriPhoto = data?.data!!
                binding.apply {
                    ivAlbum.setImageURI(uriPhoto)
                    etSongTitle.isVisible = true
                    btnUpload.isEnabled = true
                    tvInfo.isVisible = false
                }
            } else {
                Toast.makeText(requireContext(), "사진 취소.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}