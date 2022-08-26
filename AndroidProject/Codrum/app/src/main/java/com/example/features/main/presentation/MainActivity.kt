package com.example.features.main.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.base.BaseActivity
import com.example.codrum.R
import com.example.codrum.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        initView()
    }

    private fun initView() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        with(binding.bottomNavMain) {
            setupWithNavController(navController)
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.homeFragment -> {
                        navController.navigate(R.id.homeFragment)
                    }
                    R.id.profileFragment -> {
                        navController.navigate(R.id.profileFragment)
                    }
                    R.id.uploadFragment -> {
                        navController.navigate(R.id.uploadFragment)
                    }
                    R.id.logout -> {
                        checkLogin()
                    }
                }
                true
            }
        }
    }

    private fun checkLogin() {
        AlertDialog.Builder(this)
            .setTitle("로그인 하시겠습니까?")
            .setPositiveButton("네") { _, _ ->
                mainViewModel.logout()
                finish()
            }.setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
    }
}