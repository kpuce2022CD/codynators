package com.example.codrum

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.codrum.databinding.ActivityMainBinding
import com.example.codrum.fragment.HomeFragment
import com.example.codrum.fragment.ProfileFragment
import com.example.codrum.fragment.UploadFragment
import com.example.codrum.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = viewModel.getUserAuth()

        navigationItemSelect()
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
        supportFragmentManager.beginTransaction().replace(R.id.holder_fl_main,HomeFragment()).commitAllowingStateLoss()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.holder_fl_main, fragment)
        fragmentTransaction.commit()
    }

    private fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        auth?.signOut()
    }

    private fun navigationItemSelect() {
        binding.mainBottomMenu.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> replaceFragment(HomeFragment())
                    R.id.menu_profile -> replaceFragment(ProfileFragment())
                    R.id.menu_addSong -> replaceFragment(UploadFragment())
                    R.id.menu_logout -> logout()
                }
                true
            }
            selectedItemId - R.id.menu_home
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}