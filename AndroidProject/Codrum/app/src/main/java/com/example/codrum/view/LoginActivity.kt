package com.example.codrum.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.codrum.databinding.ActivityLoginBinding
import com.example.codrum.dialog.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("life", "onCreate")
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnGoRegister.setOnClickListener {
            startActivity<RegisterActivity>()
        }

        binding.btnLogin.setOnClickListener {
            if (isValidate()) {
                Log.d("valid", "enter the block")
                val email = binding.tilEmail.editText?.text.toString()
                val password = binding.tilPasswd.editText?.text.toString()
                Log.d("valid", "email = $email, password = $password")
                signIn(email, password)
            } else {
                return@setOnClickListener
            }
        }

    }

    public override fun onStart() {
        super.onStart()
        Log.d("life", "onStart")
        goMain(auth?.currentUser)
    }

    private fun signIn(email: String, password: String) {
        Log.d("signIn", "signIn start")
        val dialog = LoadingDialog(this)
        dialog.show()
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    dialog.dismiss()
                    toast("????????? ??????")
                    goMain(auth?.currentUser)
                    Log.d("signIn", "signIn Success")
                } else {
                    Log.d("signIn", "signIn Failed")
                    dialog.dismiss()
                    toast("????????? ??????")
                }
            }

    }

    //????????? ??????
    private fun isValidate(): Boolean {
        val email: String = binding.tilEmail.editText?.text.toString()
        val pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val passwd: String = binding.tilPasswd.editText?.text.toString()

        var count = 0
        // ????????? ?????????
        if (email.isEmpty()) {
            binding.tilEmail.error = "???????????? ????????? ?????????."
        } else if (!email.matches(pattern.toRegex())) {
            binding.tilEmail.error = "????????? ????????? ??????????????????."
        } else {
            binding.tilEmail.error = null
            binding.tilEmail.isErrorEnabled = false
            count++
        }

        //???????????? ????????? ??????
        if (passwd.isEmpty()) {
            binding.tilPasswd.error = "??????????????? ????????? ?????????."
        } else if (passwd.length < 6) {
            binding.tilPasswd.error = "?????? 6?????? ???????????????."
        } else {
            binding.tilPasswd.error = null
            binding.tilPasswd.isErrorEnabled = false
            count++
        }

        if (count == 2) return true

        return false
    }

    fun goMain(user: FirebaseUser?) {
        Log.d("goMain", "goMain start")
        if (user != null) {
            Log.d("goMain", "current user = ${user.toString()}")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Log.d("goMain", "User not exist")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}