package com.example.features.main.profile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.base.BaseFragment
import com.example.codrum.R
import com.example.codrum.databinding.FragmentProfileBinding
import com.example.features.main.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
