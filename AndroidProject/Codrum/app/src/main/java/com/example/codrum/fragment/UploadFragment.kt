package com.example.codrum.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.codrum.databinding.FragmentUploadBinding
import com.example.codrum.dialog.LoadingDialog
import com.example.codrum.viewModel.MainViewModel
import com.example.data.Song
import com.example.data.Song.Companion.CUSTOM
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.support.v4.toast


@AndroidEntryPoint
class UploadFragment : Fragment() {

    var pickImageFromAlbum = 0
    var uriPhoto: Uri? = null
    var fbStorage: FirebaseStorage? = null
    val userUID = Firebase.auth.currentUser?.uid.toString()

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var loadingDialog: LoadingDialog

    lateinit var binding: FragmentUploadBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(requireActivity())
        fbStorage = FirebaseStorage.getInstance()

        subscribeToObservables()
        binding.imgMusicScore.setOnClickListener {
            // Open Album
            var photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, pickImageFromAlbum)
        }
        binding.btnUpload.setOnClickListener {
            imageUpload()
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageFromAlbum) {
            if (resultCode == Activity.RESULT_OK) {
                uriPhoto = data?.data
                binding.imgMusicScore.setImageURI(uriPhoto)
                if (ContextCompat.checkSelfPermission(
                        binding.root.context,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    binding.editSongName.isVisible = true
                }
            } else {
                toast("다시 작업해 주세요")
            }
        }

    }

    private fun imageUpload() {
        val imgFileName = binding.editSongName.text.toString() + "_.jpg"
        val storageRef = fbStorage?.reference?.child(userUID)?.child(imgFileName)
        val song = Song(CUSTOM, binding.editSongName.text.toString())
        viewModel.putSong(
            song,
            binding.editSongName.text.toString(),
            storageRef,
            userUID,
            uriPhoto
        )

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