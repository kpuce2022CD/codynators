package com.example.codrum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.codrum.Dialog.LoadingDaialog
import com.example.codrum.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            val email : String = binding.tilEmail.editText?.text.toString()
            val passwd : String = binding.tilPasswd.editText?.text.toString()
            val name : String = binding.tilName.editText?.text.toString()

            val dialog = LoadingDaialog(this@MainActivity)
            if(isVaildate())
            {
                dialog.show()
                CoroutineScope(Dispatchers.Default).launch {
                     val fbRegister = async{runBlocking{
                            register(email,passwd)
                        }
                     }
                    dialog.dismiss()
                }


            }else{
                return@setOnClickListener
            }

        }

        binding.btnBack.setOnClickListener {

        }

    }

    // 형식 유효성 검사
    private fun isVaildate() : Boolean {
        val email : String = binding.tilEmail.editText?.text.toString()
        val pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val passwd : String = binding.tilPasswd.editText?.text.toString()
        val name : String = binding.tilName.editText?.text.toString()

        var count = 0

        // 이메일 유효성
        if (email.isEmpty()) {
            binding.tilEmail.error = "이메일을 입력해 주세요."
        } else if (!email.matches(pattern.toRegex())){
            binding.tilEmail.error = "이메일 형식을 맞추어주세요."
        } else{
            binding.tilEmail.error = null
            binding.tilEmail.isErrorEnabled = false
            count++
        }

        //비밀번호 유효성 검사
        if(passwd.isEmpty()){
            binding.tilPasswd.error = "비밀번호를 입력해 주세요."
        }else if(passwd.length<6){
            binding.tilPasswd.error = "최소 6자리 이상입니다."
        }else{
            binding.tilPasswd.error = null
            binding.tilPasswd.isErrorEnabled = false
            count++
        }

        //이름 유효성 검사
        if(name.isEmpty()){
            binding.tilName.error = "이름을 입력해 주세요."
        }else if(name.length>10){
            binding.tilName.error = "최대 10자리 입니다."
        }else{
            binding.tilName.error = null
            binding.tilName.isErrorEnabled = false
            count++
        }

        if(count == 3) return true

        return false

    }

    private suspend fun register(email: String, password : String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("auth", "createUserWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("auth", "createUserWithEmail:failure", task.exception)
                }
            }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}