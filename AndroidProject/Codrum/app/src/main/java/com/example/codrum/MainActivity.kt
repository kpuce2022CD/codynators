package com.example.codrum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codrum.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun isVaildateEmail() : Boolean {
        val value : String = binding.tilEmail.editText?.text.toString()
        val pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        return if (value.isEmpty()) {
            binding.tilEmail.error = "이메일을 입력해 주세요."
            false
        } else if (!value.matches(pattern.toRegex())){
            binding.tilEmail.error = "이메일 형식을 맞추어주세요."
            false
        } else{
            binding.tilEmail.error = null
            binding.tilEmail.isErrorEnabled = false
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}