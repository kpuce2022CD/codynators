package com.example.codrum

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.codrum.Dialog.LoadingDaialog
import com.example.codrum.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null

    private var _binding : ActivityLoginBinding?= null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("life","onCreate")
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnGoRegister.setOnClickListener {
            startActivity<RegisterActivity>()
        }

        binding.btnLogin.setOnClickListener {
            if(isValidate()){

                val dialog = LoadingDaialog(this)

                Log.d("valid","enter the block")
                val email = binding.tilEmail.editText?.text.toString()
                val password = binding.tilPasswd.editText?.text.toString()
                Log.d("valid","email = $email, password = $password")

                dialog.show()
                CoroutineScope(Dispatchers.Default).launch {
                    val fbLogin = async{
                        runBlocking{
                            signIn(email,password)
                        }
                    }
                    fbLogin.await()
                    dialog.dismiss()
                }
                goMain(auth?.currentUser)
            }else{
                return@setOnClickListener
            }
        }

    }

    public override fun onStart() {
        super.onStart()
        Log.d("life","onStart")
        goMain(auth?.currentUser)
    }

    private fun signIn(email : String, password: String){
        Log.d("signIn","signIn start")

        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    toast("로그인 성공")
                    Log.d("signIn","signIn Success")
                }else{
                    Log.d("signIn","signIn Failed")
                    toast("로그인 실패")
                }
            }

    }

    //유효성 검사
    private fun isValidate() : Boolean {
        val email : String = binding.tilEmail.editText?.text.toString()
        val pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val passwd : String = binding.tilPasswd.editText?.text.toString()

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

        if(count == 2) return true

        return false
    }

    fun goMain(user: FirebaseUser?){
        Log.d("goMain","goMain start")
        if(user!= null){
            Log.d("goMain","current user = ${user.toString()}")
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }else{
            Log.d("goMain","User not exist")
        }
    }
}