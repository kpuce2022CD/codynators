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
import com.example.codrum.data.Song
import com.example.codrum.databinding.FragmentUploadBinding
import com.example.codrum.dialog.LoadingDialog
import com.example.codrum.viewModel.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
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
    private val rdb = Firebase.database
    private val viewModel: MainViewModel by activityViewModels()

    val name: String = String().apply {

    }

    lateinit var binding: FragmentUploadBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadBinding.inflate(inflater, container, false)

        fbStorage = FirebaseStorage.getInstance()

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
        val dialog = LoadingDialog(binding.root.context)
        dialog.show()
        var imgFileName = binding.editSongName.text.toString() + "_.jpg"
        var storageRef = fbStorage?.reference?.child(userUID)?.child(imgFileName)
        val map = mapOf(
            "data" to ""
        )
        rdb.getReference(userUID).child(binding.editSongName.text.toString()).setValue(map)
        storageRef?.putFile(uriPhoto!!)?.addOnSuccessListener {
            toast("업로드 완료")
            dialog.dismiss()
        }

        val song = Song(userUID, binding.editSongName.text.toString())
        viewModel.putSong(song)

    }

}