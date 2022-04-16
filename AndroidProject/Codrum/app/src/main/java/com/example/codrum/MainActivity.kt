package com.example.codrum

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.codrum.Fragment.HomeFragment
import com.example.codrum.Fragment.ProfileFragment
import com.example.codrum.Fragment.UploadFragment
import com.example.codrum.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private var auth : FirebaseAuth? = null
    private var _binding : ActivityMainBinding ?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        navigationItemSelect()
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.holder_fl_main,fragment)
        fragmentTransaction.commit()
    }

    private fun logout(){
        val intent = Intent(this,LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        auth?.signOut()
    }

    private fun navigationItemSelect(){
        binding.mainBottomMenu.run{
            setOnItemSelectedListener { item ->
                when(item.itemId){
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